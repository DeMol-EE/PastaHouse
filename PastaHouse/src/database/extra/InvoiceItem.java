/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.extra;

import database.tables.Article;

/**
 *
 * @author Warkst
 */
public class InvoiceItem {
	private Article article;
	private double amount;
	private double price;
	private String articlename;
	private double taxes;
	private String lot;

	public InvoiceItem(Article article, String articlename, double amount, double price, double taxes, String lot) {
		this.article = article;
		this.amount = amount;
		this.price = price;
		this.articlename = articlename;
		this.taxes = taxes;
		this.lot = lot;
	}
	
	/**
	 * Legacy constructor to facilitaty compatibility with previous version of the db which did not yet contain the field "lot" for invoiceitem.
	 * 
	 * @param article
	 * @param articlename
	 * @param amount
	 * @param price
	 * @param taxes 
	 */
	@Deprecated
	public InvoiceItem(Article article, String articlename, double amount, double price, double taxes) {
		this(article, articlename, amount, price, taxes, "");
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getArticlename() {
		return articlename;
	}

	public void setArticlename(String articlename) {
		this.articlename = articlename;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTaxes() {
		return taxes;
	}

	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public InvoiceItem(InvoiceItem copy) {
		this.amount = copy.amount;
		this.article = new Article(copy.article);
		this.articlename = copy.articlename;
		this.price = copy.price;
		this.taxes = copy.taxes;
		this.lot = copy.lot;
	}

	public void copy(InvoiceItem copy) {
		this.amount = copy.amount;
		this.article = new Article(copy.article);
		this.articlename = copy.articlename;
		this.price = copy.price;
		this.taxes = copy.taxes;
		this.lot = copy.lot;
	}

}
