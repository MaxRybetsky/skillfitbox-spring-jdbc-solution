<#import "layout.ftl" as layout>
<@layout.layout title="Главная">
    <div class="row">
        <div class="col-12">
            <div class="jumbotron bg-primary text-white p-5 rounded mb-4">
                <h1 class="display-4">
                    <i class="fas fa-dumbbell me-3"></i>Добро пожаловать в SkillFitBox
                </h1>
                <p class="lead">Система управления фитнес-центром</p>
                <hr class="my-4">
                <p>Управляйте клиентами, тренерами, шкафчиками и услугами в одном месте.</p>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-3 mb-4">
            <div class="card h-100">
                <div class="card-body text-center">
                    <i class="fas fa-users fa-3x text-primary mb-3"></i>
                    <h5 class="card-title">Клиенты</h5>
                    <p class="card-text">Управление клиентами фитнес-центра</p>
                    <a href="/ui/clients" class="btn btn-primary">
                        <i class="fas fa-arrow-right me-1"></i>Перейти
                    </a>
                </div>
            </div>
        </div>
        
        <div class="col-md-3 mb-4">
            <div class="card h-100">
                <div class="card-body text-center">
                    <i class="fas fa-user-tie fa-3x text-success mb-3"></i>
                    <h5 class="card-title">Тренеры</h5>
                    <p class="card-text">Управление тренерами и их статусами</p>
                    <a href="/ui/trainers" class="btn btn-success">
                        <i class="fas fa-arrow-right me-1"></i>Перейти
                    </a>
                </div>
            </div>
        </div>
        
        <div class="col-md-3 mb-4">
            <div class="card h-100">
                <div class="card-body text-center">
                    <i class="fas fa-lock fa-3x text-warning mb-3"></i>
                    <h5 class="card-title">Шкафчики</h5>
                    <p class="card-text">Управление шкафчиками и их назначением</p>
                    <a href="/ui/lockers" class="btn btn-warning">
                        <i class="fas fa-arrow-right me-1"></i>Перейти
                    </a>
                </div>
            </div>
        </div>
        
        <div class="col-md-3 mb-4">
            <div class="card h-100">
                <div class="card-body text-center">
                    <i class="fas fa-concierge-bell fa-3x text-info mb-3"></i>
                    <h5 class="card-title">Услуги</h5>
                    <p class="card-text">Управление дополнительными услугами</p>
                    <a href="/ui/services" class="btn btn-info">
                        <i class="fas fa-arrow-right me-1"></i>Перейти
                    </a>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-4">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="fas fa-info-circle me-2"></i>О системе
                    </h5>
                </div>
                <div class="card-body">
                    <p>SkillFitBox - это современная система управления фитнес-центром, которая позволяет:</p>
                    <ul>
                        <li>Управлять клиентами и их данными</li>
                        <li>Назначать тренеров клиентам</li>
                        <li>Управлять шкафчиками и их распределением</li>
                        <li>Предоставлять дополнительные услуги</li>
                        <li>Отслеживать статусы и активность</li>
                    </ul>
                    <p class="mb-0">
                        <strong>API документация:</strong> 
                        <a href="/api/swagger-ui.html" target="_blank" class="btn btn-outline-primary btn-sm ms-2">
                            <i class="fas fa-external-link-alt me-1"></i>Открыть Swagger UI
                        </a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>
