<#import "../layout.ftl" as layout>
<@layout.layout title="Тренеры">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-user-tie me-2"></i>Тренеры
        </h2>
        <a href="/ui/trainers/new" class="btn btn-primary">
            <i class="fas fa-plus me-1"></i>Добавить тренера
        </a>
    </div>

    <#if trainers?has_content>
        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>ФИО</th>
                                <th>Телефон</th>
                                <th>Статус</th>
                                <th>Действия</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list trainers as trainer>
                                <tr>
                                    <td>
                                        <small class="text-muted">${trainer.id?substring(0, 8)}...</small>
                                    </td>
                                    <td>
                                        <strong>${trainer.surname} ${trainer.name} ${trainer.patronymic!""}</strong>
                                    </td>
                                    <td>${trainer.phone}</td>
                                    <td>
                                        <#if trainer.status == 'WORKING'>
                                            <span class="badge bg-success">${trainer.status}</span>
                                        <#elseif trainer.status == 'ON_LEAVE'>
                                            <span class="badge bg-warning">${trainer.status}</span>
                                        <#else>
                                            <span class="badge bg-danger">${trainer.status}</span>
                                        </#if>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="/ui/trainers/${trainer.id}" class="btn btn-sm btn-outline-primary">
                                                <i class="fas fa-eye"></i>
                                            </a>
                                            <a href="/ui/trainers/${trainer.id}/detail" class="btn btn-sm btn-outline-info">
                                                <i class="fas fa-info-circle"></i>
                                            </a>
                                            <a href="/ui/trainers/${trainer.id}/edit" class="btn btn-sm btn-outline-warning">
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
            <i class="fas fa-info-circle me-2"></i>Тренеры не найдены.
        </div>
    </#if>
</@layout.layout>
