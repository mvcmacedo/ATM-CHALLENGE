package br.com.ibm.challenge.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum MoneyBills {
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

    public static List<MoneyBills> getBills(double amount) {
        int count = 0;
        final List<MoneyBills> bills = new ArrayList<>();

        while (count != amount) {
            int pick = new Random().nextInt(MoneyBills.values().length);
            final MoneyBills bill = MoneyBills.values()[pick];

            if (count + bill.value > amount) {
                continue;
            }

            bills.add(bill);
            count = count + bill.value;
        }

        return bills;
    }
}
