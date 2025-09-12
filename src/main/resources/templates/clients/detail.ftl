<#import "../layout.ftl" as layout>
<@layout.layout title="Клиент - ${client.surname} ${client.name}">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-user me-2"></i>${client.surname} ${client.name} ${client.patronymic!""}
        </h2>
        <div class="btn-group">
            <a href="/ui/clients" class="btn btn-outline-secondary">
                <i class="fas fa-arrow-left me-1"></i>Назад к списку
            </a>
            <a href="/ui/clients/${client.id}/edit" class="btn btn-warning">
                <i class="fas fa-edit me-1"></i>Редактировать
            </a>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-info-circle me-2"></i>Основная информация
                    </h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>ID:</strong> <code>${client.id}</code></p>
                            <p><strong>Фамилия:</strong> ${client.surname}</p>
                            <p><strong>Имя:</strong> ${client.name}</p>
                            <p><strong>Отчество:</strong> ${client.patronymic!""}</p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>Дата рождения:</strong> ${client.birthday}</p>
                            <p><strong>Телефон:</strong> ${client.phone}</p>
                            <p><strong>Email:</strong> ${client.email}</p>
                            <p><strong>Статус:</strong> 
                                <#if client.isActive>
                                    <span class="badge bg-success">
                                        <i class="fas fa-check me-1"></i>Активен
                                    </span>
                                <#else>
                                    <span class="badge bg-danger">
                                        <i class="fas fa-times me-1"></i>Неактивен
                                    </span>
                                </#if>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-md-4">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-cogs me-2"></i>Действия
                    </h5>
                </div>
                <div class="card-body">
                    <div class="d-grid gap-2">
                        <a href="/ui/clients/${client.id}/detail" class="btn btn-info">
                            <i class="fas fa-info-circle me-1"></i>Подробная информация
                        </a>
                        <a href="/ui/clients/${client.id}/assign-trainer" class="btn btn-success">
                            <i class="fas fa-user-tie me-1"></i>Назначить тренера
                        </a>
                        <a href="/ui/clients/${client.id}/assign-locker" class="btn btn-warning">
                            <i class="fas fa-lock me-1"></i>Назначить шкафчик
                        </a>
                        <a href="/ui/clients/${client.id}/add-service" class="btn btn-primary">
                            <i class="fas fa-concierge-bell me-1"></i>Добавить услугу
                        </a>
                        <form method="post" action="/ui/clients/${client.id}/status" class="d-inline">
                            <input type="hidden" name="isActive" value="${(!client.isActive)?c}">
                            <#if client.isActive>
                                <button type="submit" class="btn btn-outline-danger w-100">
                                    <i class="fas fa-toggle-off me-1"></i>Деактивировать
                                </button>
                            <#else>
                                <button type="submit" class="btn btn-outline-success w-100">
                                    <i class="fas fa-toggle-on me-1"></i>Активировать
                                </button>
                            </#if>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>
