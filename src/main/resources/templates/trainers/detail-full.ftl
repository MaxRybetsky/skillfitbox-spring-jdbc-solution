<#import "../layout.ftl" as layout>
<@layout.layout title="Подробная информация - ${trainerDetail.surname} ${trainerDetail.name}">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-user-tie me-2"></i>${trainerDetail.surname} ${trainerDetail.name} ${trainerDetail.patronymic!""}
        </h2>
        <div class="btn-group">
            <a href="/ui/trainers" class="btn btn-outline-secondary">
                <i class="fas fa-arrow-left me-1"></i>Назад к списку
            </a>
            <a href="/ui/trainers/${trainerDetail.id}" class="btn btn-outline-primary">
                <i class="fas fa-user-tie me-1"></i>Основная информация
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
                    <p><strong>ID:</strong> <code>${trainerDetail.id}</code></p>
                    <p><strong>ФИО:</strong> ${trainerDetail.surname} ${trainerDetail.name} ${trainerDetail.patronymic!""}</p>
                    <p><strong>Телефон:</strong> ${trainerDetail.phone}</p>
                    <p><strong>Статус:</strong> 
                        <#if trainerDetail.status == 'WORKING'>
                            <span class="badge bg-success">${trainerDetail.status}</span>
                        <#elseif trainerDetail.status == 'ON_LEAVE'>
                            <span class="badge bg-warning">${trainerDetail.status}</span>
                        <#else>
                            <span class="badge bg-danger">${trainerDetail.status}</span>
                        </#if>
                    </p>
                </div>
            </div>
        </div>
        
        <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-users me-2"></i>Клиенты
                    </h5>
                </div>
                <div class="card-body">
                    <#if trainerDetail.clientNames?has_content>
                        <ul class="list-group list-group-flush">
                            <#list trainerDetail.clientNames as clientName>
                                <li class="list-group-item">
                                    <i class="fas fa-user me-2"></i>${clientName}
                                </li>
                            </#list>
                        </ul>
                    <#else>
                        <p class="text-muted">
                            <i class="fas fa-info-circle me-1"></i>Клиенты не назначены
                        </p>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>
