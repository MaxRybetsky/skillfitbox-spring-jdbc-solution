<#import "../layout.ftl" as layout>
<@layout.layout title="Подробная информация - ${clientDetail.surname} ${clientDetail.name}">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-user me-2"></i>${clientDetail.surname} ${clientDetail.name} ${clientDetail.patronymic!""}
        </h2>
        <div class="btn-group">
            <a href="/ui/clients" class="btn btn-outline-secondary">
                <i class="fas fa-arrow-left me-1"></i>Назад к списку
            </a>
            <a href="/ui/clients/${clientDetail.id}" class="btn btn-outline-primary">
                <i class="fas fa-user me-1"></i>Основная информация
            </a>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-info-circle me-2"></i>Основная информация
                    </h5>
                </div>
                <div class="card-body">
                    <p><strong>ID:</strong> <code>${clientDetail.id}</code></p>
                    <p><strong>ФИО:</strong> ${clientDetail.surname} ${clientDetail.name} ${clientDetail.patronymic!""}</p>
                    <p><strong>Дата рождения:</strong> ${clientDetail.birthday}</p>
                    <p><strong>Телефон:</strong> ${clientDetail.phone}</p>
                    <p><strong>Email:</strong> ${clientDetail.email}</p>
                    <p><strong>Статус:</strong> 
                        <#if clientDetail.isActive>
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
        
        <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-user-tie me-2"></i>Тренер
                    </h5>
                </div>
                <div class="card-body">
                    <#if clientDetail.trainer??>
                        <p><strong>ID:</strong> <code>${clientDetail.trainer.id}</code></p>
                        <p><strong>ФИО:</strong> ${clientDetail.trainer.surname} ${clientDetail.trainer.name} ${clientDetail.trainer.patronymic!""}</p>
                        <p><strong>Телефон:</strong> ${clientDetail.trainer.phone}</p>
                        <p><strong>Статус:</strong> 
                            <#if clientDetail.trainer.status == 'WORKING'>
                                <span class="badge bg-success">${clientDetail.trainer.status}</span>
                            <#elseif clientDetail.trainer.status == 'ON_LEAVE'>
                                <span class="badge bg-warning">${clientDetail.trainer.status}</span>
                            <#else>
                                <span class="badge bg-danger">${clientDetail.trainer.status}</span>
                            </#if>
                        </p>
                    <#else>
                        <p class="text-muted">
                            <i class="fas fa-info-circle me-1"></i>Тренер не назначен
                        </p>
                        <a href="/ui/clients/${clientDetail.id}/assign-trainer" class="btn btn-success btn-sm">
                            <i class="fas fa-user-tie me-1"></i>Назначить тренера
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-lock me-2"></i>Шкафчик
                    </h5>
                </div>
                <div class="card-body">
                    <#if clientDetail.locker??>
                        <p><strong>ID:</strong> <code>${clientDetail.locker.id}</code></p>
                        <p><strong>Номер:</strong> ${clientDetail.locker.number}</p>
                    <#else>
                        <p class="text-muted">
                            <i class="fas fa-info-circle me-1"></i>Шкафчик не назначен
                        </p>
                        <a href="/ui/clients/${clientDetail.id}/assign-locker" class="btn btn-warning btn-sm">
                            <i class="fas fa-lock me-1"></i>Назначить шкафчик
                        </a>
                    </#if>
                </div>
            </div>
        </div>
        
        <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-concierge-bell me-2"></i>Дополнительные услуги
                    </h5>
                </div>
                <div class="card-body">
                    <#if clientDetail.services?has_content>
                        <ul class="list-unstyled">
                            <#list clientDetail.services as service>
                                <li>
                                    <span class="badge bg-primary me-1">${service}</span>
                                </li>
                            </#list>
                        </ul>
                    <#else>
                        <p class="text-muted">
                            <i class="fas fa-info-circle me-1"></i>Дополнительные услуги не подключены
                        </p>
                    </#if>
                    <a href="/ui/clients/${clientDetail.id}/add-service" class="btn btn-primary btn-sm">
                        <i class="fas fa-plus me-1"></i>Добавить услугу
                    </a>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>
