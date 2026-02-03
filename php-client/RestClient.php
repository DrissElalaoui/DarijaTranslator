<?php
include 'config.php';

function translateText($text) {
    $data = ['text' => $text];
    $payload = json_encode($data);

    $ch = curl_init(API_URL);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, [
        'Content-Type: application/json'
    ]);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $payload);

    $response = curl_exec($ch);

    if(curl_errno($ch)){
        return ['error' => curl_error($ch)];
    }

    curl_close($ch);
    return json_decode($response, true);
}
