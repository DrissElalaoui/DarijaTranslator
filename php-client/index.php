<?php
include 'restClient.php';

$translation = '';
if(isset($_POST['text'])) {
    $result = translateText($_POST['text']);
    if(isset($result['translation'])){
        $translation = $result['translation'];
    } else {
        $translation = 'Erreur lors de la traduction';
    }
}
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Traduction en Darija</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<section>
        <div>
            <h2>Traduction en Darija</h2>
        </div>
    <form method="post">
        <p>Language</p>
        <textarea name="text"  placeholder="Écrire votre texte ici..."></textarea>
        <p>Darija</p>
        <textarea>
                <?php if($translation): ?>
                <p><?= htmlspecialchars($translation) ?></p>
                <?php endif; ?>
        </textarea>
        <button type="submit">Traduire</button>
    </form>
</section>
</body>
</html>
