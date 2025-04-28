package org.example.domain;

import java.util.Objects;

/**
 * Class representing venue domain object
 */
public class Venue implements Comparable<Venue> {
    private Long id;
    private String name;
    private long capacity;

    public Venue(String name, long capacity) {
        this.name = name;
        this.capacity = capacity;
        validateFields();
    }

    public static Venue createDefault() {
        return new Venue("Default Venue", 1);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Venue: id can't be null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("Venue: id must be gt 0");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Venue: name can't be null or empty");
        }
        this.name = name;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Venue: capacity must be gt 0");
        }
        this.capacity = capacity;
    }

    public void validateFields() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Venue: name can't be null or empty");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Venue: capacity must be gt 0");
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
        if (!(o instanceof Venue venue)) return false;
        return  Objects.equals(id, venue.id) && name.equals(venue.name) && capacity == venue.capacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCapacity());
    }

    @Override
    public String toString() {
        return "Venue {" +
                "\n  id=" + id +
                ",\n  name='" + name + '\'' +
                ",\n  capacity=" + capacity +
                "\n}";
    }

    @Override
    public int compareTo(Venue other) {
        int capacityCompare = Long.compare(this.capacity, other.capacity);
        if (capacityCompare != 0) {
            return capacityCompare;
        }
        return this.name.compareTo(other.name);
    }
}
