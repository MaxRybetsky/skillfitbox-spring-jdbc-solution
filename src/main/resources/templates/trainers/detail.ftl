<#import "../layout.ftl" as layout>
<@layout.layout title="Тренер - ${trainer.surname} ${trainer.name}">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-user-tie me-2"></i>${trainer.surname} ${trainer.name} ${trainer.patronymic!""}
        </h2>
        <div class="btn-group">
            <a href="/ui/trainers" class="btn btn-outline-secondary">
                <i class="fas fa-arrow-left me-1"></i>Назад к списку
            </a>
            <a href="/ui/trainers/${trainer.id}/edit" class="btn btn-warning">
                <i class="fas fa-edit me-1"></i>Редактировать
            </a>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-info-circle me-2"></i>Основная информация
                    </h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>ID:</strong> <code>${trainer.id}</code></p>
                            <p><strong>Фамилия:</strong> ${trainer.surname}</p>
                            <p><strong>Имя:</strong> ${trainer.name}</p>
                            <p><strong>Отчество:</strong> ${trainer.patronymic!""}</p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>Телефон:</strong> ${trainer.phone}</p>
                            <p><strong>Статус:</strong> 
                                <#if trainer.status == 'WORKING'>
                                    <span class="badge bg-success">${trainer.status}</span>
                                <#elseif trainer.status == 'ON_LEAVE'>
                                    <span class="badge bg-warning">${trainer.status}</span>
                                <#else>
                                    <span class="badge bg-danger">${trainer.status}</span>
                                </#if>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-md-4">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-cogs me-2"></i>Действия
                    </h5>
                </div>
                <div class="card-body">
                    <div class="d-grid gap-2">
                        <a href="/ui/trainers/${trainer.id}/detail" class="btn btn-info">
                            <i class="fas fa-info-circle me-1"></i>Подробная информация
                        </a>
                        <form method="post" action="/ui/trainers/${trainer.id}/status" class="d-inline">
                            <select name="status" class="form-select mb-2">
                                <option value="WORKING" <#if trainer.status == 'WORKING'>selected</#if>>Работает</option>
                                <option value="ON_LEAVE" <#if trainer.status == 'ON_LEAVE'>selected</#if>>В отпуске</option>
                                <option value="NOT_WORKING" <#if trainer.status == 'NOT_WORKING'>selected</#if>>Не работает</option>
                            </select>
                            <button type="submit" class="btn btn-outline-primary w-100">
                                <i class="fas fa-sync me-1"></i>Изменить статус
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>
