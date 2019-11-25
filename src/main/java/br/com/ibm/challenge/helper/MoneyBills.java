package br.com.ibm.challenge.helper;

public enum MoneyBills {
    CINCO_REAIS("cinco reais", 5),
    DEZ_REAIS("dez reais", 10),
    VINTE_REAIS("vinte reais", 20),
    CINQUENTA_REAIS("cinquenta reais", 50),
    CEM_REAIS("cem reais", 100);

    private String name;
    private int value;

    MoneyBills(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
