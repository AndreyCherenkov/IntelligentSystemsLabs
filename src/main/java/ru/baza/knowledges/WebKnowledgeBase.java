package ru.baza.knowledges;

import ru.baza.annotations.FactCondition;
import ru.baza.annotations.Rule;

/**
 * База знаний для экспертной системы по выбору инструментальных средств
 * при создании web-сайтов.
 */
public class WebKnowledgeBase implements KnowledgeBase {

    @Rule(
            all = {
                    @FactCondition(name = "complexity", value = "low"),
                    @FactCondition(name = "budget", value = "low"),
                    @FactCondition(name = "time", value = "short")
            }
    )
    public void ruleWordPress() {
        System.out.println("📦 Рекомендация: WordPress — быстрая разработка при низкой сложности и коротких сроках.");
    }

    @Rule(
            all = {
                    @FactCondition(name = "complexity", value = "low"),
                    @FactCondition(name = "budget", value = "low"),
                    @FactCondition(name = "scale", value = "personal")
            }
    )
    public void ruleWixTilda() {
        System.out.println("🌐 Рекомендация: Wix / Tilda — онлайн-конструкторы для личных проектов при низком бюджете.");
    }

    @Rule(
            all = {
                    @FactCondition(name = "complexity", value = "medium"),
                    @FactCondition(name = "budget", value = "medium"),
                    @FactCondition(name = "time", value = "medium")
            }
    )
    public void ruleHtmlCssJsNode() {
        System.out.println("💻 Рекомендация: HTML/CSS + JS + Node.js — универсальное решение для сайтов средней сложности.");
    }

    @Rule(
            all = {
                    @FactCondition(name = "complexity", value = "high"),
                    @FactCondition(name = "performance", value = "high"),
                    @FactCondition(name = "time", value = "medium")
            }
    )
    public void ruleReactDjangoPostgres() {
        System.out.println("⚙️ Рекомендация: React + Django + PostgreSQL — оптимально для сложных, производительных систем.");
    }

    @Rule(
            all = {
                    @FactCondition(name = "complexity", value = "high"),
                    @FactCondition(name = "budget", value = "high"),
                    @FactCondition(name = "scale", value = "large project"),
                    @FactCondition(name = "performance", value = "high")
            }
    )
    public void ruleAngularSpringMySQL() {
        System.out.println("🏗️ Рекомендация: Angular + Spring Boot + MySQL — крупные корпоративные проекты, высокая производительность.");
    }

    @Rule(
            all = {
                    @FactCondition(name = "complexity", value = "medium"),
                    @FactCondition(name = "time", value = "short")
            }
    )
    public void ruleVueFirebase() {
        System.out.println("🔥 Рекомендация: Vue.js + Firebase — быстрое создание динамичных сайтов средней сложности.");
    }

    @Rule(
            all = {
                    @FactCondition(name = "complexity", value = "high"),
                    @FactCondition(name = "time", value = "medium"),
                    @FactCondition(name = "scale", value = "small business")
            }
    )
    public void ruleNextMongo() {
        System.out.println("🚀 Рекомендация: Next.js + MongoDB Atlas — идеальный выбор для небольшого бизнеса с современным стеком.");
    }

    @Rule(
            all = {
                    @FactCondition(name = "complexity", value = "medium"),
                    @FactCondition(name = "budget", value = "medium"),
                    @FactCondition(name = "scale", value = "small business")
            }
    )
    public void ruleLaravelMySQL() {
        System.out.println("🧩 Рекомендация: Laravel + MySQL — надёжный вариант для бизнеса среднего уровня.");
    }

    @Rule(
            all = {
                    @FactCondition(name = "complexity", value = "high"),
                    @FactCondition(name = "scale", value = "large project"),
                    @FactCondition(name = "performance", value = "high")
            }
    )
    public void ruleAspNetSQLServer() {
        System.out.println("💼 Рекомендация: ASP.NET Core + SQL Server — решение корпоративного уровня для больших систем.");
    }
}
