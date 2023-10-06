package com.example.demo.recommendation.repository.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
public class HitCountInDB {
    @Id
    private Long productId;
    private Integer hitCount;

    public HitCountInDB() {}

    public HitCountInDB(Long productId, Integer hitCount) {
        this.productId = productId;
        this.hitCount = hitCount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HitCountInDB that = (HitCountInDB) o;
        return productId.equals(that.productId) && hitCount.equals(that.hitCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, hitCount);
    }

    @Override
    public String toString() {
        return "HitCountInDB{" +
                "productId=" + productId +
                ", hitCount=" + hitCount +
                '}';
    }
}
