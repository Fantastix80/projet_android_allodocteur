<?php

header('Content-Type: application/json');
include_once '../config/bdd.php';
$json = json_decode(file_get_contents('php://input'), true);

$result["success"] = false;
$result["error"] = "On passe pas le premier if.";

if (isset($json["prenom"]) and isset($json["nom"]) and isset($json["email"]) and isset($json["mdp"]) and isset($json["cmdp"]) and isset($json["isMedecin"])) {
    $prenom = htmlspecialchars($json["prenom"]);
    $nom = htmlspecialchars($json["nom"]);
    $email = htmlspecialchars($json["email"]);
    $mdp = htmlspecialchars($json["mdp"]);
    $cmdp = htmlspecialchars($json["cmdp"]);
    $isMedecin = htmlspecialchars($json["isMedecin"]);

    if ($prenom == "" or $nom == "" or $email == "" or $mdp == "" or $cmdp == "" or $isMedecin == "") {
        $result["success"] = false;
        $result["error"] = "Veuillez remplir tous les champs afin de pouvoir vous inscrire.";
    } else {
        $checkIfAccountExists = $bdd->prepare('SELECT email FROM utilisateur WHERE email = :email');
        $checkIfAccountExists->bindValue(":email", $email, PDO::PARAM_STR);
        $checkIfAccountExists->execute();

        if ($checkIfAccountExists->rowCount() > 0) {
            $result["success"] = false;
            $result["error"] = "Cet email est déjà associé à un compte existant.";
        } else {
            if ($mdp == $cmdp) {
                try {
                    $mdpHashed = hash("sha512", $mdp);

                    $createAccount = $bdd->prepare("INSERT INTO utilisateur (email, nom, prenom, isMedecin, mdp) VALUES (:email, :nom, :prenom, :isMedecin, :mdp);");
                    $createAccount->bindValue(":email", $email, PDO::PARAM_STR);
                    $createAccount->bindValue(":nom", $nom, PDO::PARAM_STR);
                    $createAccount->bindValue(":prenom", $prenom, PDO::PARAM_STR);
                    $createAccount->bindValue(":isMedecin", $isMedecin, PDO::PARAM_INT);
                    $createAccount->bindValue(":mdp", $mdpHashed, PDO::PARAM_STR);
                    $createAccount->execute();

                    $result["success"] = true;
                    $result["isMedecin"] = $isMedecin;
                    $result["email"] = $email;
                } catch (Exception $e) {
                    $result["success"] = false;
                    $result["error"] = "Erreur lors de la création du compte.";
                }
            } else {
                $result["success"] = false;
                $result["error"] = "Les mots de passe ne correspondent pas.";
            }
        }
    }
} else {
    $result["success"] = false;
    $result["error"] = "Veuillez remplir tous les champs afin de pouvoir vous inscrire.";
}

echo json_encode($result);
