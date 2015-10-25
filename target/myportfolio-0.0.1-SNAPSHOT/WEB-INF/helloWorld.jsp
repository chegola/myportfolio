<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>My Portfolio</title>
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
</head>
<body>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script type="text/javascript" src="./js/bootstrap.min.js"></script>
	<div class="container-fluid" style="margin-top: 10px">
		<div class="row">
			<div class="page-header">
				<h3>My Portfolio</h3>
			</div>
		</div>
	</div>


	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3>
							Cash :
							<fmt:formatNumber type="number" pattern="#,###.00"
								value="${cash.balance}" />
						</h3>
					</div>
					<div class="panel-body">
						<table class="table table-striped table-hover">
							<thead>
								<tr>
									<th>Deposit Date</th>
									<th>Amount</th>
							</thead>
							<tbody>
								<c:forEach items="${deposits}" var="deposit">
									<tr>
										<td><fmt:formatDate type="date"
												value="${deposit.txnDate}" /></td>
										<td><fmt:formatNumber type="number" pattern="#,###.00"
												value="${deposit.price}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

			</div>

			<div class="col-md-6">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3>Holdings : ${name}</h3>
					</div>
					<div class="panel-body">
						<table class="table table-striped table-hover">
							<thead>
								<tr>
									<th>Symbol</th>
									<th>Balance</th>
									<th>Cost FIFO</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${holdings}" var="holding">
									<tr>
										<td><c:out value="${holding.security.symbol}" /></td>
										<td><fmt:formatNumber type="number" pattern="#,###.00"
												value="${holding.balance}" /></td>
										<td><fmt:formatNumber type="number" pattern="#,###.00"
												value="${holding.cost}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="panel-footer">
						<button class="btn btn-default" type="button" data-toggle="modal"
							data-target="#addTxn">Add Transaction</button>
					</div>
				</div>
			</div>



			<div class="modal fade" id="addTxn">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">x</button>
							<h4 class="modal-title">Add Transaction</h4>
						</div>
						<div class="modal-body">

							<spring:url value="/myPortfolio/addTxn" var="addTxnUrl" />

<%-- 							<form:form class="form-horizontal" method="post"
								modelAttribute="userForm" action="${userActionUrl}">

							</form:form>
 --%>
<%-- 
							<form:form class="form-horizontal"  method="POST" 
								commandName="addTxn" modelAttribute="addTxn" action="${addTxnUrl}"> --%>

							<form:form class="form-horizontal">
								<fieldset>
									<spring:bind path="txnDate">
										<div class="form-group">
											<label for="textArea" class="col-lg-2 control-label">Date</label>
											<div class="col-lg-10">
												<!-- <textarea class="form-control" rows="1" id="textArea"></textarea> -->
												<form:input path="txnDate" type="text" class="form-control"
													id="txnDate" placeholder="txnDate" />
											</div>
										</div>
									</spring:bind>

									<div class="form-group">
										<label for="select" class="col-lg-2 control-label">Types</label>
										<div class="col-lg-10">
											<select class="form-control" id="select">
												<option>BUY</option>
												<option>SELL</option>
												<option>DEPOSIT</option>
												<option>WITHDROW</option>
												<option>DIVIDEND</option>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label for="symbol" class="col-lg-2 control-label">Symbol</label>
										<div class="col-lg-10">
											<select class="form-control" id="symbol">
												<c:forEach items="${symbols}" var="symbol">
													<option>${symbol.symbol}</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label for="price" class="col-lg-2 control-label">Price</label>
										<div class="col-lg-10">
											<textarea class="form-control" rows="1" id="price"></textarea>
										</div>
									</div>

									<div class="form-group">
										<label for="Amount" class="col-lg-2 control-label">Amount</label>
										<div class="col-lg-10">
											<textarea class="form-control" rows="1" id="amount"></textarea>
										</div>
									</div>

								</fieldset>
							</form:form>


						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">Save</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div>


			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3>Dividend</h3>
					</div>
					<div class="panel-body">
						<table class="table table-striped table-hover">
							<thead>
								<tr>
									<th>Symbol</th>
									<th>Date</th>
									<th>Amount</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${dividends}" var="dividend">
									<tr>
										<td><c:out value="${dividend.security.symbol}" /></td>
										<td><fmt:formatDate type="date"
												value="${dividend.txnDate}" /></td>
										<td><fmt:formatNumber type="number" pattern="#,###.00"
												value="${dividend.price}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

			</div>

		</div>
	</div>

</body>
</html>