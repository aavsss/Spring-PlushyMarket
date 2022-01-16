package com.example.demo.buy.model;

public class BuyRequestBody {
    private Long id;
    private Integer amount;

    public BuyRequestBody() {
    }

    public BuyRequestBody(Long id, Integer amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BuyRequestBody{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
