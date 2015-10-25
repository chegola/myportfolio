<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>My Portfolio</title>
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/bootstrap-datepicker.css" />
</head>
<body>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script type="text/javascript" src="./js/bootstrap.min.js"></script>
	<script src="text/javascript" src="./js/bootstrap-datepicker.*js"></script>



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
								<tr align="center">
									<th>Deposit Date</th>
									<th>Amount</th>
							</thead>
							<tbody>
								<c:forEach items="${deposits}" var="deposit">
									<tr>
										<td><fmt:formatDate type="date"
												value="${deposit.txnDate}" /></td>
										<td align="right"><fmt:formatNumber type="number" pattern="#,###.00"
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
								<tr align="right">
									<th>Symbol</th>
									<th align="right">Balance</th>
									<th align="right">Cost FIFO</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${holdings}" var="holding">
									<tr>
										<td><c:out value="${holding.security.symbol}" /></td>
										<td align="right"><fmt:formatNumber type="number" pattern="#,###.00"
												value="${holding.balance}" /></td>
										<td align="right"><fmt:formatNumber type="number" pattern="#,###.00"
												value="${holding.cost}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="panel-footer">
						<button class="btn btn-default" type="button" data-toggle="modal"
							data-target="#addTxn">Add Transaction
						</button>
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
								<tr align="center">
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
										<td align="right"><fmt:formatNumber type="number" pattern="#,###.00"
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

			<spring:url value="/myPortfolio/addTxn" var="addTxnUrl" />
			<form:form class="form-horizontal" method="POST"
				modelAttribute="userParam" action="${addTxnUrl}">
				<fieldset>
					<div class="modal fade" id="addTxn">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">x</button>
									<h4 class="modal-title">Add Transaction</h4>
								</div>
								<div class="modal-body">
									<spring:bind path="tradeDate">
										<div class="form-group">
											<form:label path="tradeDate" class="col-lg-2 control-label">Date</form:label>
											<div class='col-lg-10'>
												<div class='input-group date' id='datepicker1'>
													<form:input path="tradeDate" type='text'
														class="form-control" />
													<span class="input-group-addon"> <span
														class="glyphicon glyphicon-calendar"></span>
													</span>
												</div>
											</div>
										</div>
									</spring:bind>

									<spring:bind path="type">
										<div class="form-group">
											<form:label path="type" for="select"
												class="col-lg-2 control-label">Types</form:label>
											<div class="col-lg-10">
												<form:select path="type" class="form-control" id="select">
													<option>BUY</option>
													<option>SELL</option>
													<option>DEPOSIT</option>
													<option>WITHDROW</option>
													<option>DIVIDEND</option>
												</form:select>
											</div>
										</div>
									</spring:bind>

									<spring:bind path="securitySymbol">
										<div class="form-group">
											<form:label path="securitySymbol" for="symbol"
												class="col-lg-2 control-label">Symbol</form:label>
											<div class="col-lg-10">
												<form:select path="securitySymbol" class="form-control"
													id="symbol">
													<c:forEach items="${symbols}" var="symbol">
														<option>${symbol.symbol}</option>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</spring:bind>

									<spring:bind path="price">
										<div class="form-group">
											<form:label path="price" for="price"
												class="col-lg-2 control-label">Price</form:label>
											<div class="col-lg-10">
												<form:textarea path="price" class="form-control" rows="1"
													id="price"></form:textarea>
											</div>
										</div>
									</spring:bind>

									<spring:bind path="share">
										<div class="form-group">
											<form:label path="share" for="Amount"
												class="col-lg-2 control-label">Amount</form:label>
											<div class="col-lg-10">
												<form:textarea path="share" class="form-control" rows="1"
													id="amount"></form:textarea>
											</div>

										</div>
									</spring:bind>
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary">Save</button>
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
								</div>
							</div>
						</div>
					</div>
				</fieldset>
			</form:form>


	<script src="./js/bootstrap-datepicker.js"></script>
	<script type="text/javascript">
		$(function() {
			$('#datepicker1').datepicker({
				format : "dd/mm/yyyy",
				todayBtn : "linked",
				clearBtn : true,
				autoclose : true,
				todayHighlight : true
			});
		});
	</script>
	<!--  <script type="text/javascript">
            // When the document is ready
            $(document).ready(function () {
                
                $('#example1').datepicker({
                    format: "dd/mm/yyyy"
                });  
            
            });
        </script> -->

</body>
</html>