<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>QCM - ${mcq.title}</title>
    
    <!-- Import des polices Apple et Samsung -->
    <style>
        @font-face {
            font-family: 'SamsungOne';
            src: url('https://cdn.jsdelivr.net/gh/danielkalen/SamsungOne@latest/SamsungOne-400.woff2') format('woff2');
            font-weight: normal;
            font-style: normal;
        }

        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap');

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'SamsungOne', 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            background: linear-gradient(135deg, #f8f9fa, #e9ecef);
            color: #333;
            display: flex;
            flex-direction: column;
            align-items: center;
            height: 100vh;
        }

        /* 🔹 Barre de navigation */
        .navbar {
            width: 100%;
            background: #fff;
            padding: 15px 20px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1000;
        }

        .navbar a {
            text-decoration: none;
            font-size: 16px;
            font-weight: 500;
            color: #007aff;
            transition: color 0.3s ease;
        }

        .navbar a:hover {
            color: #005ecb;
        }

        .container {
            background: #fff;
            width: 100%;
            max-width: 500px;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            text-align: center;
            margin-top: 80px; /* Pour éviter que la navbar cache le contenu */
        }

        h1 {
            font-size: 24px;
            font-weight: 600;
            color: #222;
            margin-bottom: 15px;
            font-family: 'Inter', sans-serif;
        }

        p {
            font-size: 16px;
            color: #555;
            margin-bottom: 25px;
            line-height: 1.6;
        }

        .options {
            text-align: left;
            margin-bottom: 25px;
        }

        .options label {
            display: flex;
            align-items: center;
            background: #f1f3f5;
            padding: 12px 15px;
            border-radius: 8px;
            margin-bottom: 10px;
            transition: 0.3s ease-in-out;
            cursor: pointer;
            font-family: 'SamsungOne', sans-serif;
            font-size: 17px; /* 🔹 Texte légèrement agrandi */
        }

        /* 🔹 Ajout d'un espace entre le bouton radio et le texte */
        .options label input {
            margin-right: 10px;
            transform: scale(1.2); /* 🔹 Agrandit légèrement le bouton radio */
        }

        .options label:hover {
            background: #dee2e6;
        }

        button {
            background: #007aff;
            color: white;
            border: none;
            padding: 12px 20px;
            font-size: 16px;
            border-radius: 8px;
            cursor: pointer;
            transition: background 0.3s;
        }

        button:hover {
            background: #005ecb;
        }
    </style>
</head>
<body>

    <!-- 🔹 Barre de navigation -->
    <div class="navbar">
        <a href="${pageContext.request.contextPath}/">🏠 Accueil</a>
    </div>

    <div class="container">
        <h1>${mcq.title}</h1>
        <p>${mcq.description}</p>

        <form action="${pageContext.request.contextPath}/mcq/${mcq.id}/submit" method="post">
            <div class="options">
                <c:forEach var="entry" items="${entries}">
                    <label>
                        <input type="radio" name="answerId" value="${entry.id}" required> ${entry.answer}
                    </label>
                </c:forEach>
            </div>
            <button type="submit">Valider</button>
        </form>
    </div>

</body>
</html>
