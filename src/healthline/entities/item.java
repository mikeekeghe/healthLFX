
package healthline.entities;

/**
 *
 * @author Eche Michael
 */
public class item {
    private String itemName;
    private String details;
    private String quantity;
    private String units;
    private String costPrice;
    private String createdDate;
    private String formated_date;

    public item(String itemName, String details, String quantity, String units, String costPrice, String createdDate, String formated_date) {
        this.itemName = itemName;
        this.details = details;
        this.quantity = quantity;
        this.units = units;
        this.costPrice = costPrice;
        this.createdDate = createdDate;
        this.formated_date = formated_date;
    }

    public item() {
    }

    public String getFormated_date() {
        return formated_date;
    }

    public void setFormated_date(String formated_date) {
        this.formated_date = formated_date;
    }
    
    




    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
