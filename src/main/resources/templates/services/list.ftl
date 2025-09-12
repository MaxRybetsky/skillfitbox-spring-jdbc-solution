<#import "../layout.ftl" as layout>
<@layout.layout title="Услуги">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-concierge-bell me-2"></i>Дополнительные услуги
        </h2>
    </div>

    <#if services?has_content>
        <div class="row">
            <#list services as service>
                <div class="col-md-6 mb-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">
                                <i class="fas fa-concierge-bell me-2"></i>${service.name}
                            </h5>
                            <p class="card-text">
                                <small class="text-muted">ID: ${service.id}</small>
                            </p>
                            <#if service.clientNames?has_content>
                                <h6>Клиенты:</h6>
                                <ul class="list-unstyled">
                                    <#list service.clientNames as clientName>
                                        <li>
                                            <i class="fas fa-user me-1"></i>${clientName}
                                        </li>
                                    </#list>
                                </ul>
                            <#else>
                                <p class="text-muted">
                                    <i class="fas fa-info-circle me-1"></i>Клиенты не подключены
                                </p>
                            </#if>
                        </div>
                        <div class="card-footer">
                            <a href="/ui/services/${service.id}" class="btn btn-primary">
                                <i class="fas fa-eye me-1"></i>Подробнее
                            </a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
        
        <div class="row mt-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">
                            <i class="fas fa-chart-pie me-2"></i>Статистика
                        </h5>
                        <p><strong>Всего услуг:</strong> ${services?size}</p>
                        <p><strong>Всего подключений:</strong> 
                            <#list services as service>
                                ${(service.clientNames?size)!0}<#if service_has_next> + </#if>
                            </#list>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    <#else>
        <div class="alert alert-info">
            <i class="fas fa-info-circle me-2"></i>Услуги не найдены.
        </div>
    </#if>
</@layout.layout>
