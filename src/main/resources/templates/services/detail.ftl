<#import "../layout.ftl" as layout>
<@layout.layout title="Услуга - ${service.name}">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-concierge-bell me-2"></i>${service.name}
        </h2>
        <a href="/ui/services" class="btn btn-outline-secondary">
            <i class="fas fa-arrow-left me-1"></i>Назад к списку
        </a>
    </div>

    <div class="row">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-info-circle me-2"></i>Информация об услуге
                    </h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>ID:</strong> <code>${service.id}</code></p>
                            <p><strong>Название:</strong> ${service.name}</p>
                            <p><strong>Цена:</strong> ${service.price!0}₽</p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>Количество клиентов:</strong> ${service.clientNames?size}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-md-4">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-users me-2"></i>Клиенты
                    </h5>
                </div>
                <div class="card-body">
                    <#if service.clientNames?has_content>
                        <ul class="list-group list-group-flush">
                            <#list service.clientNames as clientName>
                                <li class="list-group-item">
                                    <i class="fas fa-user me-2"></i>${clientName}
                                </li>
                            </#list>
                        </ul>
                    <#else>
                        <p class="text-muted">
                            <i class="fas fa-info-circle me-1"></i>Клиенты не подключены
                        </p>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>
