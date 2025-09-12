<#import "../layout.ftl" as layout>
<@layout.layout title="Клиенты">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-users me-2"></i>Клиенты
        </h2>
        <a href="/ui/clients/new" class="btn btn-primary">
            <i class="fas fa-plus me-1"></i>Добавить клиента
        </a>
    </div>

    <#if clients?has_content>
        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>ФИО</th>
                                <th>Дата рождения</th>
                                <th>Телефон</th>
                                <th>Email</th>
                                <th>Статус</th>
                                <th>Действия</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list clients as client>
                                <tr>
                                    <td>
                                        <small class="text-muted">${client.id?substring(0, 8)}...</small>
                                    </td>
                                    <td>
                                        <strong>${client.surname} ${client.name} ${client.patronymic!""}</strong>
                                    </td>
                                    <td>${client.birthday}</td>
                                    <td>${client.phone}</td>
                                    <td>${client.email}</td>
                                    <td>
                                        <#if client.isActive>
                                            <span class="badge bg-success">
                                                <i class="fas fa-check me-1"></i>Активен
                                            </span>
                                        <#else>
                                            <span class="badge bg-danger">
                                                <i class="fas fa-times me-1"></i>Неактивен
                                            </span>
                                        </#if>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="/ui/clients/${client.id}" class="btn btn-sm btn-outline-primary">
                                                <i class="fas fa-eye"></i>
                                            </a>
                                            <a href="/ui/clients/${client.id}/detail" class="btn btn-sm btn-outline-info">
                                                <i class="fas fa-info-circle"></i>
                                            </a>
                                            <a href="/ui/clients/${client.id}/edit" class="btn btn-sm btn-outline-warning">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    <#else>
        <div class="alert alert-info">
            <i class="fas fa-info-circle me-2"></i>Клиенты не найдены.
        </div>
    </#if>
</@layout.layout>
