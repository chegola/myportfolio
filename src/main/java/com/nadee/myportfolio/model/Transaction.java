package com.nadee.myportfolio.model;

import java.io.Serializable;

import javax.persistence.*;

import org.eclipse.persistence.annotations.ConversionValue;
import org.eclipse.persistence.annotations.ObjectTypeConverter;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="transaction")
@NamedQuery(name="Txn.findById", query="SELECT t FROM Transaction t where t.transactionId = :transactionId")
@Data
@NoArgsConstructor
public class Transaction implements Serializable  {

	private static final long serialVersionUID = -1889759590686653171L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="txn_id",nullable=false)
	private Integer transactionId;

	private String note;
	
	@ManyToOne
	@JoinColumn(name="portfolio_id")
	private Portfolio portfolio;

	@Column(name="price")
	private Double price;

	@Column(name="share")
	private Double share;
	
	@Transient
	private Double amount;

	@ManyToOne
	@JoinColumn(name="sec_id")
	private Security security;

	@Temporal(TemporalType.DATE)
	@Column(name="txn_date")
	private Date txnDate;

	@Column(name="txn_type_id", nullable=false)
    @ObjectTypeConverter(name = "TransactionType", objectType = TransactionType.class, dataType = Integer.class,
    defaultObjectValue = "BUY", conversionValues = {
    	@ConversionValue (objectValue = "BUY", dataValue = "1"),
    	@ConversionValue(objectValue = "SELL", dataValue = "2"),
    	@ConversionValue(objectValue = "DEPOSIT", dataValue = "3"),
    	@ConversionValue(objectValue = "WITHDRAW", dataValue = "4"),
    	@ConversionValue(objectValue = "DIVIDEND", dataValue = "5")
    })
    @Convert
	private Integer txnType;

	/*	@OneToMany(mappedBy = "portfolio")
	private final List<Holding> holdings = new ArrayList<Holding>();
	*/

	}