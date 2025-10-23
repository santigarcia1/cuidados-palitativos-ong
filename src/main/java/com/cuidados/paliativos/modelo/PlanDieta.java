package com.cuidados.paliativos.modelo;

public class PlanDieta {
    private PlanCuidado plan;
    private Dieta dieta;

    public PlanDieta() {}

    public PlanDieta(PlanCuidado plan, Dieta dieta) {
        this.plan = plan;
        this.dieta = dieta;
    }

    public PlanCuidado getPlan() {
        return plan;
    }

    public void setPlan(PlanCuidado plan) {
        this.plan = plan;
    }

    public Dieta getDieta() {
        return dieta;
    }

    public void setDieta(Dieta dieta) {
        this.dieta = dieta;
    }
}
