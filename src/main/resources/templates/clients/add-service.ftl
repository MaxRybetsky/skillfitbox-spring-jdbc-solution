<#import "../layout.ftl" as layout>
<@layout.layout title="Добавить услугу - ${client.surname} ${client.name}">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-concierge-bell me-2"></i>Добавить услугу
        </h2>
        <a href="/ui/clients/${client.id}" class="btn btn-outline-secondary">
            <i class="fas fa-arrow-left me-1"></i>Назад к клиенту
        </a>
    </div>

    <div class="row">
        <div class="col-md-4">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-user me-2"></i>Клиент
                    </h5>
                </div>
                <div class="card-body">
                    <p><strong>ФИО:</strong> ${client.surname} ${client.name} ${client.patronymic!""}</p>
                    <p><strong>Телефон:</strong> ${client.phone}</p>
                    <p><strong>Email:</strong> ${client.email}</p>
                </div>
            </div>
        </div>
        
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-concierge-bell me-2"></i>Доступные услуги
                    </h5>
                </div>
                <div class="card-body">
                    <#if services?has_content>
                        <div class="row">
                            <#list services as service>
                                <div class="col-md-6 mb-3">
                                    <div class="card">
                                        <div class="card-body">
                                            <h6 class="card-title">${service.name}</h6>
                                            <p class="card-text">
                                                <small class="text-muted">ID: ${service.id}</small>
                                            </p>
                                            <#if service.clientNames?has_content>
                                                <p class="card-text">
                                                    <small class="text-muted">
                                                        Клиенты: ${service.clientNames?join(", ")}
                                                    </small>
                                                </p>
                                            </#if>
                                            <form method="post" action="/ui/clients/${client.id}/services/${service.id}" class="d-inline">
                                                <button type="submit" class="btn btn-primary btn-sm">
                                                    <i class="fas fa-plus me-1"></i>Добавить
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    <#else>
                        <div class="alert alert-info">
                            <i class="fas fa-info-circle me-2"></i>Услуги не найдены.
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>
