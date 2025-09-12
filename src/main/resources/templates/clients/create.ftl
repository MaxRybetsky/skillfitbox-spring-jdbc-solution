<#import "../layout.ftl" as layout>
<@layout.layout title="Добавить клиента">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>
            <i class="fas fa-user-plus me-2"></i>Добавить клиента
        </h2>
        <a href="/ui/clients" class="btn btn-outline-secondary">
            <i class="fas fa-arrow-left me-1"></i>Назад к списку
        </a>
    </div>

    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-info-circle me-2"></i>Информация о клиенте
                    </h5>
                </div>
                <div class="card-body">
                    <form method="post" action="/ui/clients">
                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <label for="surname" class="form-label">Фамилия *</label>
                                <input type="text" class="form-control" id="surname" name="surname" 
                                       value="${(client.surname)!""}" required>
                            </div>
                            <div class="col-md-4 mb-3">
                                <label for="name" class="form-label">Имя *</label>
                                <input type="text" class="form-control" id="name" name="name" 
                                       value="${(client.name)!""}" required>
                            </div>
                            <div class="col-md-4 mb-3">
                                <label for="patronymic" class="form-label">Отчество</label>
                                <input type="text" class="form-control" id="patronymic" name="patronymic" 
                                       value="${(client.patronymic)!""}">
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="birthday" class="form-label">Дата рождения *</label>
                                <input type="date" class="form-control" id="birthday" name="birthday" 
                                       value="${(client.birthday)!""}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="phone" class="form-label">Телефон *</label>
                                <input type="tel" class="form-control" id="phone" name="phone" 
                                       value="${(client.phone)!""}" required>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-8 mb-3">
                                <label for="email" class="form-label">Email *</label>
                                <input type="email" class="form-control" id="email" name="email" 
                                       value="${(client.email)!""}" required>
                            </div>
                            <div class="col-md-4 mb-3">
                                <label for="isActive" class="form-label">Статус</label>
                                <select class="form-select" id="isActive" name="isActive">
                                    <option value="true" <#if (client.isActive!true)>selected</#if>>Активен</option>
                                    <option value="false" <#if !(client.isActive!true)>selected</#if>>Неактивен</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="d-flex justify-content-end gap-2">
                            <a href="/ui/clients" class="btn btn-secondary">
                                <i class="fas fa-times me-1"></i>Отмена
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-save me-1"></i>Создать клиента
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>
