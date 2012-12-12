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
    private int rank;
    private double amount;
    private double save;

    public InvoiceItem(Article article, int rank, double amount, double save) {
	this.article = article;
	this.rank = rank;
	this.amount = amount;
	this.save = save;
    }

    public Article getArticle() {
	return article;
    }

    public void setArticle(Article article) {
	this.article = article;
    }

    public int getRank() {
	return rank;
    }

    public void setRank(int rank) {
	this.rank = rank;
    }

    public double getAmount() {
	return amount;
    }

    public void setAmount(double amount) {
	this.amount = amount;
    }

    public double getSave() {
	return save;
    }

    public void setSave(double save) {
	this.save = save;
    }
    
    
}
