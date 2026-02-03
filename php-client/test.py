
import numpy as np
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn.preprocessing import LabelEncoder
from sklearn import tree
import matplotlib.pyplot as plt

print("--- SOLUTION EXERCICE 1 (SANS PANDAS) ---")

# 1. Saisie des données brutes
raw_age = [
    'Jeune', 'Jeune', 'Moyen', 'Agé', 'Agé', 'Agé', 'Moyen',
    'Jeune', 'Jeune', 'Agé', 'Jeune', 'Moyen', 'Moyen', 'Agé'
]

raw_salaire = [
    'élevé', 'élevé', 'élevé', 'Moyen', 'faible', 'faible', 'faible',
    'Moyen', 'faible', 'Moyen', 'Moyen', 'Moyen', 'élevé', 'Moyen'
]

raw_etudes = [
    'non', 'non', 'non', 'non', 'oui', 'oui', 'oui',
    'non', 'oui', 'oui', 'oui', 'non', 'oui', 'non'
]

raw_target = [
    'non', 'non', 'oui', 'oui', 'oui', 'non', 'oui',
    'non', 'oui', 'oui', 'oui', 'oui', 'oui', 'non'
]

# 2. Encodage (un LabelEncoder par variable)
le_age = LabelEncoder()
le_salaire = LabelEncoder()
le_etudes = LabelEncoder()
le_target = LabelEncoder()

age_encoded = le_age.fit_transform(raw_age)
salaire_encoded = le_salaire.fit_transform(raw_salaire)
etudes_encoded = le_etudes.fit_transform(raw_etudes)
y = le_target.fit_transform(raw_target)

# 3. Création de la matrice X
X = np.column_stack((age_encoded, salaire_encoded, etudes_encoded))

print("Matrice X (5 premières lignes) :\n", X[:5])
print("Cible Y (5 premières valeurs) :", y[:5])

# 4. Partition des données
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.3, random_state=42
)

# 5. Apprentissage (ID3 ≈ entropy)
clf = DecisionTreeClassifier(criterion='entropy')
clf.fit(X_train, y_train)

# 6. Évaluation
y_pred = clf.predict(X_test)
accuracy = accuracy_score(y_test, y_pred)
error_rate = 1 - accuracy

print(f"\nPrécision (Accuracy): {accuracy:.2f}")
print(f"Taux d'erreur: {error_rate:.2f}")

# 7. Affichage de l'arbre
plt.figure(figsize=(12, 6))
tree.plot_tree(
    clf,
    feature_names=["Age", "Salaire", "Etudes"],
    class_names=["Non", "Oui"],
    filled=True
)
plt.show()
