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

    var data = {"OkPercent": 94.78333333333333, "KoPercent": 5.216666666666667};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.0028333333333333335, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.0, 500, 1500, "gRPC-[1000-100-10] Request"], "isController": false}, {"data": [0.0125, 500, 1500, "gRPC-[1-100-10] Request"], "isController": false}, {"data": [0.0015, 500, 1500, "REST-[1000-100-10] Request"], "isController": false}, {"data": [0.0, 500, 1500, "REST-[1-100-10] Request"], "isController": false}, {"data": [5.0E-4, 500, 1500, "gRPC-[100-100-10] Request"], "isController": false}, {"data": [0.0025, 500, 1500, "REST-[100-100-10] Request"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 6000, 313, 5.216666666666667, 7803.682999999977, 29, 26326, 6146.0, 15515.0, 17278.9, 19269.749999999993, 43.43608380268435, 10.11061807511185, 6.01765088609611], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["gRPC-[1000-100-10] Request", 1000, 59, 5.9, 13322.814000000015, 508, 26326, 13095.0, 17627.7, 18451.649999999998, 20657.89, 7.26158404194291, 1.7993056450646643, 1.0076227911169042], "isController": false}, {"data": ["gRPC-[1-100-10] Request", 1000, 59, 5.9, 4432.496000000002, 29, 13485, 3163.5, 9299.699999999999, 12976.849999999999, 13387.96, 20.36950278043713, 5.048354494276169, 2.7703319465096854], "isController": false}, {"data": ["REST-[1000-100-10] Request", 1000, 29, 2.9, 13512.303000000005, 477, 24703, 13168.5, 17728.1, 18603.8, 21060.440000000006, 7.246219285088006, 1.3177009172807839, 1.037546794725477], "isController": false}, {"data": ["REST-[1-100-10] Request", 1000, 56, 5.6, 4479.000000000009, 509, 13471, 3081.0, 9215.399999999996, 12819.8, 13349.99, 20.073468896159945, 4.840842483489572, 2.7387739125198225], "isController": false}, {"data": ["gRPC-[100-100-10] Request", 1000, 57, 5.7, 6324.737000000009, 515, 14626, 5521.0, 11638.0, 13969.95, 14462.86, 15.264612049884754, 3.7143243357985685, 2.108573607867381], "isController": false}, {"data": ["REST-[100-100-10] Request", 1000, 53, 5.3, 4750.747999999997, 110, 13599, 3981.0, 9121.5, 12819.9, 13414.89, 19.705598360494214, 4.624215009015312, 2.7335744600666048], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 75, 23.961661341853034, 1.25], "isController": false}, {"data": ["Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 238, 76.03833865814697, 3.966666666666667], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 6000, 313, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 238, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 75, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["gRPC-[1000-100-10] Request", 1000, 59, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 45, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 14, null, null, null, null, null, null], "isController": false}, {"data": ["gRPC-[1-100-10] Request", 1000, 59, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 49, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 10, null, null, null, null, null, null], "isController": false}, {"data": ["REST-[1000-100-10] Request", 1000, 29, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 20, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 9, null, null, null, null, null, null], "isController": false}, {"data": ["REST-[1-100-10] Request", 1000, 56, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 40, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 16, null, null, null, null, null, null], "isController": false}, {"data": ["gRPC-[100-100-10] Request", 1000, 57, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 39, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 18, null, null, null, null, null, null], "isController": false}, {"data": ["REST-[100-100-10] Request", 1000, 53, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Broken pipe (Write failed)", 45, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset by peer (connect failed)", 8, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
