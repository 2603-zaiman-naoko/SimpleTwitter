package chapter6.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class User implements Serializable {

    private int id;
    private String account;
    private String name;
    private String email;
    private String password;
    private String description;
    private Date createdDate;
    private Date updatedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getAccount() {
        return account;
	}

	public void setAccount(String account) {
		// TODO 自動生成されたメソッド・スタブ
        this.account = account;
	}

	public String getPassword() {
        return password;
	}

	public void setPassword(String password) {
		// TODO 自動生成されたメソッド・スタブ
        this.password = password;
	}

	public String getEmail() {
		// TODO 自動生成されたメソッド・スタブ
		return email;
	}

	public void setEmail(String email) {
		// TODO 自動生成されたメソッド・スタブ
        this.email = email;
	}

	public String getDescription() {
		// TODO 自動生成されたメソッド・スタブ
		return description;
	}

	public void setDescription(String description) {
		// TODO 自動生成されたメソッド・スタブ
        this.description = description;
	}

	public Date getCreatedDate() {
		// TODO 自動生成されたメソッド・スタブ
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		// TODO 自動生成されたメソッド・スタブ
        this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		// TODO 自動生成されたメソッド・スタブ
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		// TODO 自動生成されたメソッド・スタブ
        this.updatedDate = updatedDate;
	}
}

