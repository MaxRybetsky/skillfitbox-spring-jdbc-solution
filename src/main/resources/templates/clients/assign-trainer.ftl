<#import "../layout.ftl" as layout>
<@layout.layout title="Назначить тренера - ${client.surname} ${client.name}">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-user-tie me-2"></i>Назначить тренера
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
                        <i class="fas fa-user-tie me-2"></i>Доступные тренеры
                    </h5>
                </div>
                <div class="card-body">
                    <#if trainers?has_content>
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead class="table-light">
                                    <tr>
                                        <th>ФИО</th>
                                        <th>Телефон</th>
                                        <th>Статус</th>
                                        <th>Действие</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <#list trainers as trainer>
                                        <tr>
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
                                                <form method="post" action="/ui/clients/${client.id}/trainer/${trainer.id}" class="d-inline">
                                                    <button type="submit" class="btn btn-success btn-sm">
                                                        <i class="fas fa-user-plus me-1"></i>Назначить
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    <#else>
                        <div class="alert alert-info">
                            <i class="fas fa-info-circle me-2"></i>Тренеры не найдены.
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>
