package br.com.silvaaraujo.model.enuns;

public enum StatusTask {
	PENDENTE("Pendente"),
	CONCLUIDA("Concluída"),
	REMOVIDA("Removida");
	
	private String label;
	
	private StatusTask(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
