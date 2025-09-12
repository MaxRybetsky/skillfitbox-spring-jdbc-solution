<#import "../layout.ftl" as layout>
<@layout.layout title="Шкафчики">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-lock me-2"></i>Шкафчики
        </h2>
    </div>

    <#if lockers?has_content>
        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Номер</th>
                                <th>Статус</th>
                                <th>Клиент</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list lockers as locker>
                                <tr>
                                    <td>
                                        <small class="text-muted">${locker.id?substring(0, 8)}...</small>
                                    </td>
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
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        <div class="row mt-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">
                            <i class="fas fa-chart-pie me-2"></i>Статистика
                        </h5>
                        <#assign occupiedCount = 0>
                        <#assign freeCount = 0>
                        <#list lockers as locker>
                            <#if locker.clientFullName??>
                                <#assign occupiedCount = occupiedCount + 1>
                            <#else>
                                <#assign freeCount = freeCount + 1>
                            </#if>
                        </#list>
                        <p><strong>Всего шкафчиков:</strong> ${lockers?size}</p>
                        <p><strong>Занято:</strong> <span class="text-danger">${occupiedCount}</span></p>
                        <p><strong>Свободно:</strong> <span class="text-success">${freeCount}</span></p>
                    </div>
                </div>
            </div>
        </div>
    <#else>
        <div class="alert alert-info">
            <i class="fas fa-info-circle me-2"></i>Шкафчики не найдены.
        </div>
    </#if>
</@layout.layout>
