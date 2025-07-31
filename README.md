# 🗂️ Taskboard API

Une API RESTful permettant la gestion d’un tableau de tâches (Taskboard), avec authentification sécurisée et gestion des utilisateurs.

## 🔧 Technologies

- Java 17  
- Spring Boot  
- JWT (Json Web Token)  
- Maven  
- Postman (pour les tests API)
- Postgresql

---

## 📌 Endpoints

### 🔐 Signup – Création d'un compte utilisateur  
L'utilisateur soumet ses informations (nom, email, mot de passe, etc.) pour s'inscrire.  
> Une fois inscrit, un code d'activation est envoyé par email.

![Création d'utilisateur](./signup.png)

---

### 🔑 Login – Connexion  
L'utilisateur entre ses identifiants. Si la connexion est réussie, un access token et un refresh token lui sont retournés.

![Connexion d'utilisateur](./login.png)

---

### 🔁 Refresh Token  
Lorsque l'access token expire, l'utilisateur peut obtenir un nouveau jeton à l'aide du refresh token.

![refresh token](./refresh-token.png)

---

### ✅ Activation du compte  
L'utilisateur active son compte en soumettant le code reçu par email.

![Activation compte d'utilisateur](./activate.png)

---

### 📧 Code d'activation par email  
Exemple de mail contenant le code d’activation envoyé automatiquement après inscription.

![code d'activation par mail](./mail-activate.png)

---

### 🚪 Logout – Déconnexion  
Permet de se déconnecter et d’invalider les tokens associés à l’utilisateur.

![Déconnexion d'utilisateur](./logout.png)

---


## 🧑‍💻 Auteur

**Assane KANE**  
[GitHub : Logibuilder](https://github.com/Logibuilder)

---

