document.getElementById("translateBtn").addEventListener("click", () => {
    const text = document.getElementById("inputText").value;

    fetch("http://localhost:8080/darija-jee-1.0-SNAPSHOT/api/translate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ text })
    })
    .then(res => res.json())
    .then(data => {
        document.getElementById("resultText").value = data.translation || "";
    })
    .catch(err => {
        document.getElementById("resultText").value = "Erreur";
    });
});
