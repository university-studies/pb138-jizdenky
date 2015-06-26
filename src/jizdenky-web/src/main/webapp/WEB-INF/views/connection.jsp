<%--
  Created by IntelliJ IDEA.
  User: pavol
  Date: 25.5.2014
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <link rel="stylesheet" href="<c:url value="/resources/bootstrap.css" />" >
        <link rel="stylesheet" href="<c:url value="/resources/select2.css" />" >
        <link rel="stylesheet" href="<c:url value="/resources/jquery-ui-1.10.4.custom.css" />" >

        <script src="<c:url value="/resources/jquery-2.1.1.js" />"></script>
        <script src="<c:url value="/resources/jquery-ui-1.10.4.custom.js" />"></script>
        <script src="<c:url value="/resources/connection.js" />"></script>
        <script src="<c:url value="/resources/select2.js" />"></script>

        <title>Jizdenky</title>
    </head>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Jizdenky</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">Seach</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>

        <div class="container">
            <div class="row" style="padding-top: 60px">
            </div>  <!-- row -->
            <div class="row">
                <form id="form" class="form-horizontal" role="form" method="post" action="/">
                    <fieldset>
                        <legend>Connections</legend>
                        <div class="form-group">
                            <label for="from" class="col-sm-2 control-label">From</label>
                            <div class="col-sm-10">
                                <select id="from"  name="from" multiple>
                                    <c:forEach items="${stations}" var="station">
                                        <option <c:if test="${station eq input.from}"> selected="selected" </c:if>>
                                            <c:out value="${station.getName()}"/>
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="to" class="col-sm-2 control-label">To</label>
                            <div class="col-sm-10">
                                <select id="to" name="to" multiple>
                                    <c:forEach items="${stations}" var="station">
                                        <option<c:if test="${station eq input.to}"> selected="selected" </c:if>>
                                            <c:out value="${station.getName()}"/></option>
                                        </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="date" class="col-sm-2 control-label">Date</label>
                            <div class="col-sm-10">
                                <input type="text" id="datepicker" name="date" value="${input == null ? A : input.dayOfDeparture.toString()}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="passengers" class="col-sm-2 control-label">Passengers</label>
                            <div class="col-lg-1">
                                <input id="passengers" name="passengers" class="form-control" type="number" min="1" max="50" 
                                       value="${input == null ? 1 : fn:length(input.passengers)}">
                            </div>
                        </div>

                        <div id="passenger-type-1-div" class="form-group" 
                             style="${input == null ? 'visibility: visible' : fn:length(input.passengers) > 2 ? 'visibility: hidden' : 'visibility: visible'}}">
                            <label for="passenger-type-1" class="col-sm-2 control-label">Passenger 1</label>
                            <div class="col-lg-2">
                                <select id="passenger-type-1" name="passenger-type-1" class="form-control">
                                    <option <c:if test="${input.passengers[0].type.name() eq 'NORMAL'}">selected="selected"</c:if> value="normal">Normal</option>>
                                    <option <c:if test="${input.passengers[0].type.name() eq 'STUDENT'}">selected="selected"</c:if> value="student">Student</option>>
                                    <option <c:if test="${input.passengers[0].type.name() eq 'SENIOR'}">selected="selected"</c:if> value="senior">Senior</option>>
                                    </select>
                                </div>
                            </div>
                            <div id="passenger-type-2-div" class="form-group" 
                                 style="${input == null ? 'visibility: hidden' : fn:length(input.passengers) == 2 ? 'visibility: visible' : 'visibility: hidden'}}">
                            <label for="passenger-type-2" class="col-sm-2 control-label">Passenger 2</label>
                            <div class="col-lg-2">
                                <select id="passenger-type-2" name="passenger-type-2" class="form-control">
                                    <option <c:if test="${input.passengers[1].type.name() eq 'NORMAL'}">selected="selected"</c:if> value="normal">Normal</option>>
                                    <option <c:if test="${input.passengers[1].type.name() eq 'STUDENT'}">selected="selected"</c:if>value="student">Student</option>>
                                    <option <c:if test="${input.passengers[1].type.name() eq 'SENIOR'}">selected="selected"</c:if> value="senior">Senior</option>>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <input type="submit" name="sbmBtn" class="btn btn-default">
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>  <!-- row -->

            <c:if test="${error != null}">
                <c:out value="${error}" />
            </c:if>

            <div class="row">
                <c:if test="${result.journeys.size() > 0}">
                    <h2><c:out value="${input.from.name}"/> --> <c:out value="${input.to.name}"/>
                        <small><c:out value="${input.dayOfDeparture}"/></small></h2>
                    <h3>Applicated discounts: 
                        <small>
                            <c:forEach items="${result.discounts}" var="discount" varStatus="loop">
                                <c:out value="${discount.name}" />
                                <c:if test="${!loop.last}">
                                    <c:out value=", "/>
                                </c:if>
                            </c:forEach>
                        </small>
                    </h3>
                </c:if>

                <%--Journeys --%>
                <c:forEach items="${result.journeys}" var="journey">
                    <div class="col-lg-12">
                        <h4> Price: <c:out value="${journey.price}"/> </h4>
                        Total length: ${journey.getTotalLength()} km

                        <%--tracks--%>
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <c:forEach items="${journey.getTracks()}" var="track" varStatus="loopParent">
                                    <%-- Stations --%>
                                    <c:forEach items="${track.getStations()}" var="station" varStatus="loop">
                                        <c:if test="${! station.equals(station_old)}">
                                            <c:out value="${station.getName()}"/><c:if test="${! loopParent.last}"><c:out value="," escapeXml="false"/>
                                            </c:if>
                                        </c:if>
                                        <c:set var="station_old" scope="session" value="${station}"/>
                                    </c:forEach>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div><!-- /.container -->
    </body>
</html>
