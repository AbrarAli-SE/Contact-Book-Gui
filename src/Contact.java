public class Contact {
    private String name;
    private String phone;
    private String email;
    private boolean favorite;
    private boolean blocked;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.favorite = false;
        this.blocked = false;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}