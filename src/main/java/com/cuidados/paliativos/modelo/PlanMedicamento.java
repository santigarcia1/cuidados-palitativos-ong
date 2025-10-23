package com.cuidados.paliativos.modelo;

public class PlanMedicamento {
    private PlanCuidado plan;
    private Medicamento medicamento;

    public PlanMedicamento() {}

    public PlanMedicamento(PlanCuidado plan, Medicamento medicamento) {
        this.plan = plan;
        this.medicamento = medicamento;
    }

    public PlanCuidado getPlan() {
        return plan;
    }

    public void setPlan(PlanCuidado plan) {
        this.plan = plan;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }
}
