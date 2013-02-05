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

    public InvoiceItem(Article article, double amount) {
	this.article = article;
	this.amount = amount;
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
    
}
