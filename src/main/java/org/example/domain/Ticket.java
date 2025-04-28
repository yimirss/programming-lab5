package org.example.domain;

import org.example.domain.enums.TicketType;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Class representing ticket domain object
 */
public class Ticket implements Comparable<Ticket> {
    private long id;
    private String name;
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private Integer price;
    private long discount;
    private boolean refundable;
    private TicketType type;
    private Venue venue;

    public Ticket(String name, Coordinates coordinates, Integer price, long discount, boolean refundable, TicketType type, Venue venue) {
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
        this.refundable = refundable;
        this.type = type;
        this.venue = venue;
        validateFields();
    }

    public static Ticket createDefault() {
        return new Ticket(
                "Default Ticket",
                Coordinates.createDefault(),
                null,
                1,
                false,
                TicketType.USUAL,
                Venue.createDefault()
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Ticket: id must be gt 0");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Ticket: name cannot be null or empty");
        }
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Ticket: coordinates cannot be null");
        }
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("Ticket: creation date cannot be null");
        }
        this.creationDate = creationDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        if (price != null && price <= 0) {
            throw new IllegalArgumentException("Ticket: price must be gt 0");
        }
        this.price = price;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        if (discount <= 0 || discount > 100) {
            throw new IllegalArgumentException("Ticket: discount must be gt 0 and lte 100");
        }
        this.discount = discount;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        if (type == null) {
            throw new IllegalArgumentException("Ticket: ticket type cannot be null");
        }
        this.type = type;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        if (venue == null) {
            throw new IllegalArgumentException("Venue cannot be null");
        }
        this.venue = venue;
    }

    public void validateFields() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Ticket: name can't be null or empty");
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("Ticket: coordinates can't be null");
        }
        if (price != null && price <= 0) {
            throw new IllegalArgumentException("Ticket: price must be gt 0");
        }
        if (discount <= 0 || discount > 100) {
            throw new IllegalArgumentException("Ticket: discount must be gt 0 and lte 100");
        }
        if (type == null) {
            throw new IllegalArgumentException("Ticket: ticket type can't be null");
        }
        if (venue == null) {
            throw new IllegalArgumentException("Ticket: venue can't be null");
        }
    }

    public boolean isValid() {
        try {
            validateFields();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket ticket)) return false;
        return id == ticket.id && discount == ticket.discount && refundable == ticket.refundable &&
                name.equals(ticket.name) && coordinates.equals(ticket.coordinates) &&
                creationDate.equals(ticket.creationDate) && Objects.equals(price, ticket.price) && type == ticket.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, price, discount, refundable, type, venue);
    }

    @Override
    public String toString() {
        return "Ticket {" +
                "\n  id=" + id +
                ",\n  name='" + name + '\'' +
                ",\n  coordinates=" + coordinates +
                ",\n  creationDate=" + creationDate +
                ",\n  price=" + price +
                ",\n  discount=" + discount +
                ",\n  refundable=" + refundable +
                ",\n  type=" + type +
                ",\n  venue=" + venue +
                "\n}";
    }

    @Override
    public int compareTo(Ticket other) {
        int nameCompare = this.name.compareTo(other.name);
        if (nameCompare != 0) {
            return nameCompare;
        }

        if (this.price != null && other.price != null) {
            int priceCompare = this.price.compareTo(other.price);
            if (priceCompare != 0) {
                return priceCompare;
            }
        } else if (this.price != null) {
            return 1;
        } else if (other.price != null) {
            return -1;
        }

        int discountCompare = Long.compare(this.discount, other.discount);
        if (discountCompare != 0) {
            return discountCompare;
        }

        int typeCompare = this.type.compareTo(other.type);
        if (typeCompare != 0) {
            return typeCompare;
        }

        return this.venue.compareTo(other.venue);
    }
}