/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 94.58333333333333, "KoPercent": 5.416666666666667};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.009166666666666667, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.016, 500, 1500, "gRPC-[1-100-10] Request"], "isController": false}, {"data": [0.0, 500, 1500, "gRPC-[1000-100-10] Request"], "isController": false}, {"data": [0.0, 500, 1500, "REST-[1000-100-10] Request"], "isController": false}, {"data": [0.028, 500, 1500, "REST-[1-100-10] Request"], "isController": false}, {"data": [0.0, 500, 1500, "gRPC-[100-100-10] Request"], "isController": false}, {"data": [0.011, 500, 1500, "REST-[100-100-10] Request"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 6000, 325, 5.416666666666667, 9665.902166666694, 15, 39644, 8059.0, 18605.4, 21857.34999999999, 30165.809999999998, 37.43238774962723, 8.87921947817692, 5.174930126209534], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["gRPC-[1-100-10] Request", 1000, 52, 5.2, 6188.124999999997, 15, 19189, 5249.5, 13929.699999999992, 17867.85, 19002.93, 14.971404617181184, 3.4803252350510525, 2.051316360750966], "isController": false}, {"data": ["gRPC-[1000-100-10] Request", 1000, 67, 6.7, 15587.623999999983, 1938, 39644, 13747.5, 24811.6, 29978.149999999998, 34325.53, 6.240132790025772, 1.6560422718295447, 0.8585240506417977], "isController": false}, {"data": ["REST-[1000-100-10] Request", 1000, 27, 2.7, 15621.633000000022, 546, 35840, 13721.0, 24513.899999999998, 27357.8, 31942.000000000007, 6.240522206898273, 1.1077841056239586, 0.895386957230581], "isController": false}, {"data": ["REST-[1-100-10] Request", 1000, 66, 6.6, 6110.691000000002, 32, 20342, 4861.0, 14102.099999999986, 17764.9, 18904.36, 15.057292999864485, 3.9634794769096415, 2.032616919880144], "isController": false}, {"data": ["gRPC-[100-100-10] Request", 1000, 59, 5.9, 8088.305999999991, 1830, 27682, 6602.0, 15913.799999999996, 20020.649999999998, 20570.510000000002, 11.898954081936196, 2.9486979383574683, 1.6401732115872016], "isController": false}, {"data": ["REST-[100-100-10] Request", 1000, 54, 5.4, 6399.033999999995, 125, 19340, 5652.0, 14301.599999999979, 18128.3, 19013.11, 14.587040872888526, 3.454763990795577, 2.0213877928348456], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 59, 18.153846153846153, 0.9833333333333333], "isController": false}, {"data": ["Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 266, 81.84615384615384, 4.433333333333334], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 6000, 325, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 266, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 59, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["gRPC-[1-100-10] Request", 1000, 52, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 44, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 8, null, null, null, null, null, null], "isController": false}, {"data": ["gRPC-[1000-100-10] Request", 1000, 67, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 53, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 14, null, null, null, null, null, null], "isController": false}, {"data": ["REST-[1000-100-10] Request", 1000, 27, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 23, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 4, null, null, null, null, null, null], "isController": false}, {"data": ["REST-[1-100-10] Request", 1000, 66, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 55, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 11, null, null, null, null, null, null], "isController": false}, {"data": ["gRPC-[100-100-10] Request", 1000, 59, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 47, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 12, null, null, null, null, null, null], "isController": false}, {"data": ["REST-[100-100-10] Request", 1000, 54, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 44, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 10, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
