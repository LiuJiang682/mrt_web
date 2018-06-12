import React, { Component } from "react";
import ol from "openlayers";

export default class Map extends Component {
    constructor(props) {
        super(props);
        this.state = {
            map: null
        }
    }
    render() {
        console.log(this.props.match.params.id);
        return (
            <div>
                <h2>Map: {this.props.match.params.id}</h2>
                <div id='map'>{this.state.map}</div>
            </div>
        )
    }

    getTestPolygon() {
        var myPolygon = new ol.geom.Polygon([[
            ol.proj.fromLonLat([142.182273433333, -35.6700773416667], 'EPSG:3857'),
            ol.proj.fromLonLat([142.167381741667, -35.6702226583333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.1671741, -35.6717005166667], 'EPSG:3857'),
            ol.proj.fromLonLat([142.160824191667, -35.6702860666667], 'EPSG:3857'),
            ol.proj.fromLonLat([142.147118283333, -35.670417425], 'EPSG:3857'),
            ol.proj.fromLonLat([142.1471158, -35.6856514083333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.131645, -35.6856818083333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.133549491667, -35.6839782083333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.1357885, -35.6779579083333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.1407558, -35.6754986083333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.14400355, -35.6704470666667], 'EPSG:3857'),
            ol.proj.fromLonLat([142.143498583333, -35.6704518666667], 'EPSG:3857'),
            ol.proj.fromLonLat([142.1404216, -35.6752428083333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.136925983333, -35.6705141083333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.127036808333, -35.6706070833333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.1271894, -35.6814514166667], 'EPSG:3857'),
            ol.proj.fromLonLat([142.127248325, -35.68563905], 'EPSG:3857'),
            ol.proj.fromLonLat([142.1275442, -35.7066657333333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.171753766667, -35.7062434083333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.17135795, -35.6791998666667], 'EPSG:3857'),
            ol.proj.fromLonLat([142.182406341667, -35.6790861666667], 'EPSG:3857'),
            ol.proj.fromLonLat([142.167383, -35.6717812083333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.182406341667, -35.6790861666667], 'EPSG:3857')
        ]]);
        var myNewF = new ol.Feature({
            geometry: myPolygon
        });

        var myInterPolygon = new ol.geom.Polygon([[
            ol.proj.fromLonLat([142.152154758333, -35.6837831083333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.152593725, -35.6837796166667], 'EPSG:3857'),
            ol.proj.fromLonLat([142.154921525, -35.6837611], 'EPSG:3857'),
            ol.proj.fromLonLat([142.154946458333, -35.6855667333333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.152180058333, -35.685581525], 'EPSG:3857'),
            ol.proj.fromLonLat([142.149413658333, -35.6855962416667], 'EPSG:3857'),
            ol.proj.fromLonLat([142.149387966667, -35.68380505], 'EPSG:3857'),
            ol.proj.fromLonLat([142.149831358333, -35.6838015333333], 'EPSG:3857'),
            ol.proj.fromLonLat([142.152154758333, -35.6837831083333], 'EPSG:3857')
        ]]);
        var myNewInterF = new ol.Feature({
            geometry: myInterPolygon
        });
        var myPolygonSource = new ol.source.Vector();
        myPolygonSource.addFeature(myNewF);
        myPolygonSource.addFeature(myNewInterF);
        var polygonVector = new ol.layer.Vector({
            source: myPolygonSource,
            visible: true
        });
        return polygonVector;
    }

    buildPolygonsFromPosList(posList) {
        var polygons = [];
        for (var string of posList) {
            var polygonPos = [];
            var poses = string.split(" ");
            var index = 0;
            while (index < poses.length) {
                const latPos = parseFloat(poses[index]);
                const lonPos = parseFloat(poses[++index]);

                polygonPos.push(ol.proj.fromLonLat([lonPos, latPos], 'EPSG:3857'));
                ++index;
            }
            var polygon = new ol.geom.Polygon([polygonPos]);
            polygons.push(polygon);
        }
        return polygons;
    }

    componentDidMount() {
        console.log('componentDidMount');

        const lon = 142.152154758333;
        const lat = -35.6837831083333;

        var center = ol.proj.fromLonLat([lon, lat]);

        var polygonVector = this.getTestPolygon();

        var vectorSource = new ol.source.Vector({
            format: new ol.format.GML2(),
            projection: 'epsg:3857',
            loader: function () {
                var proxyUrl = 'https://cors-anywhere.herokuapp.com/';
                var url = 'http://geology.data.vic.gov.au/nvcl/ows?service=WFS&version=1.1.0&request=GetFeature&typeNames=mt:MineralTenement&cql_filter=mt:name=%27EL006759%27';
                var xhr = new XMLHttpRequest();
                var xml;
                fetch(proxyUrl + url, {
                    headers: {
                        'Access-Control-Allow-Headers': '*',
                        'x-requested-with': 'XMLHttpRequest'
                    }
                })
                    .then(response => response.text())
                    .then(str => (new window.DOMParser()).parseFromString(str, "text/xml"))
                    .then(data => {
                        console.log(data);
                        var posList = [];
                        var shapeXml = data.getElementsByTagNameNS('http://www.opengis.net/gml', 'posList');
                        console.log(shapeXml);
                        for (const shape of shapeXml) {
                            posList.push(shape.firstChild.textContent);
                        }

                        var polygons = [];
                        for (var string of posList) {
                            var polygonPos = [];
                            var poses = string.split(" ");
                            var index = 0;
                            while (index < poses.length) {
                                const latPos = parseFloat(poses[index]);
                                const lonPos = parseFloat(poses[++index]);

                                polygonPos.push(ol.proj.fromLonLat([lonPos, latPos], 'EPSG:3857'));
                                ++index;
                            }
                            var polygon = new ol.geom.Polygon([polygonPos]);
                            polygons.push(polygon);
                        }
                        for (const p of polygons) {
                            var wfsF = new ol.Feature({
                                geometry: p
                            });
                            vectorSource.addFeature(wfsF);
                        }
                    });
            }
        });

        var vector = new ol.layer.Vector({
            name: 'wfs',
            source: vectorSource,
            visible: true
        });

        var layers = [
            new ol.layer.Tile({
                source: new ol.source.OSM(),
                name: 'osm'
            })
            ,
            // polygonVector
            vector
        ];
        let map = new ol.Map({
            layers: layers,
            target: 'map',
            view: new ol.View({
                center: center,
                zoom: 11
            })
        });
    }
}