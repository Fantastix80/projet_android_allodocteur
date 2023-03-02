<?php

header('Content-Type: application/json');
include_once '../config/bdd.php';
$json = json_decode(file_get_contents('php://input'), true);

if (isset($json["email"]) and isset($json["mdp"])) {
    $email = htmlspecialchars($json["email"]);
    $mdp = htmlspecialchars($json["mdp"]);
    $mdpHashed = hash("sha512", $mdp);

    $getUser = $bdd->prepare("SELECT isMedecin, mdp FROM utilisateur WHERE email = :email");
    $getUser->bindValue(":email", $email, PDO::PARAM_STR);
    $getUser ->execute();

    if ($getUser->rowCount() > 0) {
        $user = $getUser->fetch();
        
        if ($mdpHashed == $user["mdp"]) {
            $result["success"] = true;
            $result["isMedecin"] = $user["isMedecin"];
            $result["email"] = $email;
        } else {
            $result["success"] = false;
            $result["error"] = "Le nom d'utilisateur ou le mot de passe est incorrect.";
        }
    } else {
        $result["success"] = false;
        $result["error"] = "Le nom d'utilisateur ou le mot de passe est incorrect.";
    }
    
} else {
    $result["success"] = false;
    $result["error"] = "Veuillez remplir tous les champs afin de pouvoir vous inscrire.";
}

echo json_encode($result);