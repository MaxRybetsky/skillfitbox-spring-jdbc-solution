<#import "../layout.ftl" as layout>
<@layout.layout title="Назначить шкафчик - ${client.surname} ${client.name}">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-lock me-2"></i>Назначить шкафчик
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
                        <i class="fas fa-lock me-2"></i>Доступные шкафчики
                    </h5>
                </div>
                <div class="card-body">
                    <#if lockers?has_content>
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead class="table-light">
                                    <tr>
                                        <th>Номер</th>
                                        <th>Статус</th>
                                        <th>Клиент</th>
                                        <th>Действие</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <#list lockers as locker>
                                        <tr>
                                            <td>
                                                <strong>#${locker.number}</strong>
                                            </td>
                                            <td>
                                                <#if locker.clientFullName??>
                                                    <span class="badge bg-danger">
                                                        <i class="fas fa-times me-1"></i>Занят
                                                    </span>
                                                <#else>
                                                    <span class="badge bg-success">
                                                        <i class="fas fa-check me-1"></i>Свободен
                                                    </span>
                                                </#if>
                                            </td>
                                            <td>
                                                <#if locker.clientFullName??>
                                                    ${locker.clientFullName}
                                                <#else>
                                                    <span class="text-muted">-</span>
                                                </#if>
                                            </td>
                                            <td>
                                                <#if !locker.clientFullName??>
                                                    <form method="post" action="/ui/clients/${client.id}/locker/${locker.id}" class="d-inline">
                                                        <button type="submit" class="btn btn-success btn-sm">
                                                            <i class="fas fa-lock me-1"></i>Назначить
                                                        </button>
                                                    </form>
                                                <#else>
                                                    <span class="text-muted">Занят</span>
                                                </#if>
                                            </td>
                                        </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    <#else>
                        <div class="alert alert-info">
                            <i class="fas fa-info-circle me-2"></i>Шкафчики не найдены.
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>
