<#macro layout title="SkillFitBox">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title} - Система управления фитнес-центром</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .navbar-brand {
            font-weight: bold;
        }
        .card {
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
            border: 1px solid rgba(0, 0, 0, 0.125);
        }
        .btn-group .btn {
            margin-right: 5px;
        }
        .status-active {
            color: #198754;
        }
        .status-inactive {
            color: #dc3545;
        }
        .status-working {
            color: #198754;
        }
        .status-on-leave {
            color: #ffc107;
        }
        .status-not-working {
            color: #dc3545;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="/ui">
                <i class="fas fa-dumbbell me-2"></i>SkillFitBox
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/ui/clients">
                            <i class="fas fa-users me-1"></i>Клиенты
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/ui/trainers">
                            <i class="fas fa-user-tie me-1"></i>Тренеры
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/ui/lockers">
                            <i class="fas fa-lock me-1"></i>Шкафчики
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/ui/services">
                            <i class="fas fa-concierge-bell me-1"></i>Услуги
                        </a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/api/swagger-ui.html" target="_blank">
                            <i class="fas fa-book me-1"></i>API Документация
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <main class="container mt-4">
        <#if success??>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle me-2"></i>${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </#if>
        
        <#if error??>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </#if>

        <#nested>
    </main>

    <footer class="bg-light mt-5 py-4">
        <div class="container text-center">
            <p class="text-muted mb-0">&copy; 2024 SkillFitBox. Система управления фитнес-центром.</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
</#macro>
