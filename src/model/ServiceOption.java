package model;

public enum ServiceOption {
    Shower(1), Grooming(2), ShowerAndGrooming(3), Complete(4)
    ;

    ServiceOption(int i) {
    }

    public static float getPrice(ServiceOption serviceOption){
        float price;
        switch (serviceOption) {
            case Shower -> {
                price = 25;
            }
            case Grooming -> {
                price = 20;
            }
            case ShowerAndGrooming -> {
                price = 40;
            }
            case Complete -> {
                price = 60;
            }
            default -> {
                price = 0;
            }
        }
        return price;
    }
}
