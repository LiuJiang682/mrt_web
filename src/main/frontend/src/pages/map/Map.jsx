import React, { Component } from "react";
import { Link } from 'react-router-dom';
import ol from "openlayers";
import Proj4 from "proj4";

export default class Map extends Component {
    constructor(props) {
        super(props);
        this.state = {
            map: null,
            batchId: null,
            tenement: null,
            center: null,
        }
    }
    render() {
        // console.log(this.props.match.params.id);
        let linkId = this.state.batchId;
        return (
            <div>
                <h2>Map for Batch: <Link to={"/logs/" + linkId}>{this.state.batchId}</Link>&ensp;Tenement: {this.state.tenement}</h2>
                <div id='map'>{this.state.map}</div>
                <div id="popup" class="ol-popup">
                	<a href="#" id="popup-closer" class="ol-popup-closer"></a>
                	<div id="popup-content"></div>
              </div>
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

    componentWillMount() {
        // console.log('componentWillMount');
        const tenement = this.extractTenement(this.props.match.params.id);
        this.setState({
            batchId: tenement[0],
            tenement: tenement[1],
        });
        
        Proj4.defs("EPSG:4283", "+proj=longlat +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +no_defs");
        Proj4.defs("EPSG:28354", "+proj=utm +zone=54 +south +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs");
        Proj4.defs("EPSG:28355", "+proj=utm +zone=55 +south +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs");
        ol.proj.setProj4(Proj4);
        var mel = ol.proj.transform([320721.52, 5812855.79], 'EPSG:28355', 'EPSG:3857');
        // console.log('mel', mel);
        this.setState({
            center: mel
        });
    }

    componentDidMount() {
        // console.log('componentDidMount');
        // console.log(this.props.match.params.id);
        const tenement = this.extractTenement(this.props.match.params.id);
        
        const circelStyle = new ol.style.Style({
            image: new ol.style.Circle({
                radius: 5,
                fill: new ol.style.Fill({
                    color: 'rgba(230,120,30,0.7)'
                })
            })
        });

        var boreHoleMarkerStyle = [
            new ol.style.Style({
                image: new ol.style.Icon(({
                    scale: 1,
                    rotateWithView: false,
                    rotation: 0,
                    anchor: [0.5, 1],
                    anchorXUnits: 'fraction',
                    anchorYUnits: 'fraction',
                    opacity: 1,
                    src: 'http://maps.google.com/mapfiles/ms/icons/red.png'
                }))
            }),
            circelStyle
        ];

        var sampleMarkerStyle = [
            new ol.style.Style({
                image: new ol.style.Icon(({
                    scale: 1,
                    rotateWithView: false,
                    rotation: 0,
                    anchor: [0.5, 1],
                    anchorXUnits: 'fraction',
                    anchorYUnits: 'fraction',
                    opacity: 1,
                    src: 'http://maps.google.com/mapfiles/ms/icons/blue.png'
                }))
            }),
            circelStyle
        ];

        var vectorSource = new ol.source.Vector({
            format: new ol.format.GML2(),
            projection: 'epsg:3857',
            loader: function () {
                var proxyUrl = 'https://cors-anywhere.herokuapp.com/';
                var url = 'http://geology.data.vic.gov.au/nvcl/ows?service=WFS&version=1.1.0&request=GetFeature&typeNames=mt:MineralTenement&cql_filter=mt:name=%27' + tenement[1] + '%27';
                // var url = 'http://geology.data.vic.gov.au/nvcl/ows?service=WFS&version=1.1.0&request=GetFeature&typeNames=mt:MineralTenement&cql_filter=mt:name=%27EL006759%27';
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
                        // console.log(data);
                        var posList = [];
                        var shapeXml = data.getElementsByTagNameNS('http://www.opengis.net/gml', 'posList');
                        // console.log(shapeXml);
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

        const markerSource = new ol.source.Vector();
        const boreHoles = [];
        const siteUrl = 'http://localhost:8090/site/search/get?sessionId=' + tenement[0];
        fetch(siteUrl)
            .then(response => response.json())
            .then(data => {
                // console.log(data);
                for (const site of data._embedded.site) {
                    const boreHoleId = this.extractBoreHoleId(site._links.self.href);
                    // console.log(boreHoleId);
                    const amgZone = site.amgZone;
                    const easting = parseFloat(site.easting);
                    const northing = parseFloat(site.northing);
                    const boreHoleDto = {boreHoleId: boreHoleId, amgZone: amgZone, easting: easting, northing: northing};
                    boreHoles.push(boreHoleDto);
                }
                for (const boreHole of boreHoles) {
                    var zone = this.extractAmgZone(boreHole.amgZone);
                    var point = ol.proj.transform([boreHole.easting, boreHole.northing], zone, 'EPSG:3857');
                    // console.log('point', point);
                    var iconFeature = new ol.Feature({
                        geometry: new ol.geom.Point(point),
                        properties: {'sampleId': boreHole.boreHoleId}
                    });
                    iconFeature.setStyle(boreHoleMarkerStyle);
                    markerSource.addFeature(iconFeature);
                }    
            });

        const samples = [];
        const sampleUrl = 'http://localhost:8090/surfaceGeochemistry/search/get?sessionId=' + tenement[0];    
        fetch(sampleUrl)
            .then(response => response.json())
            .then(data => {
                // console.log(data);
                for (const surfaceGeochemistry of data._embedded.surfaceGeochemistry) {
                    const sampleId =surfaceGeochemistry.sampleId;
                    const amgZone = surfaceGeochemistry.amgZone;
                    const easting = parseFloat(surfaceGeochemistry.easting);
                    const northing = parseFloat(surfaceGeochemistry.northing);
                    const sampleDto = {sampleId: sampleId, amgZone: amgZone, easting: easting, northing: northing};
                    samples.push(sampleDto);
                }
                for (const sample of samples) {
                    var sampleZone = this.extractAmgZone(sample.amgZone);
                    var samplePoint = ol.proj.transform([sample.easting, sample.northing], sampleZone, 'EPSG:3857');
                    // console.log('sample point', samplePoint);
                    var sampleIconFeature = new ol.Feature({
                        geometry: new ol.geom.Point(samplePoint),
                        properties: {'sampleId': sample.sampleId}
                    });
                    sampleIconFeature.setStyle(sampleMarkerStyle);
                    markerSource.addFeature(sampleIconFeature);
                }
            });

        var tenementVector = new ol.layer.Vector({
            name: 'tenement',
            source: vectorSource,
            visible: true
        });

        var markerVector = new ol.layer.Vector({
            name: 'boreHoles',
            source: markerSource
        });

        var layers = [
            new ol.layer.Tile({
                source: new ol.source.OSM(),
                name: 'osm'
            })
            ,
            // polygonVector
            tenementVector,
            markerVector
        ];
        let map = new ol.Map({
            layers: layers,
            target: 'map',
            view: new ol.View({
                center: this.state.center,
                zoom: 7
            })
        });

        var container = document.getElementById('popup');
        var content = document.getElementById('popup-content');
        var closer = document.getElementById('popup-closer');
        
        /**
         * Create an overlay to anchor the popup to the map.
         */
        var overlay = new ol.Overlay({
          element: container,
          autoPan: true,
          autoPanAnimation: {
            duration: 250
          }
        });


        /**
         * Add a click handler to hide the popup.
         * @return {boolean} Don't follow the href.
         */
        closer.onclick = function() {
          overlay.setPosition(undefined);
          closer.blur();
          return false;
        };

        map.addOverlay(overlay);

        map.on('pointermove', function (evt) {
            var feature = map.forEachFeatureAtPixel(evt.pixel, function (feature, layer) {
                return feature;
            });

            if (feature) {
                var coord = feature.getGeometry().getCoordinates();
                var props = feature.getProperties();
                var desc = props.properties.sampleId;
                var info = "<a href=\"/logs/" + tenement[0] + "\">" + desc + "</a>";
                content.innerHTML = info;
                overlay.setPosition(coord);
            }
        });

        map.getViewport().addEventListener('mouseout', function(evt) {
            overlay.setPosition(undefined);
            closer.blur();
        }, false);
    }

    extractBoreHoleId(url) {
        //  console.log(url);
        if ((undefined !== url) 
            && (null !== url)) {
                let index = url.lastIndexOf("/");
                return url.substring(++index);
            }
        return '';
    }

    extractAmgZone(amgZone) {
        let zone;
        if ('54' === amgZone) {
            zone = 'EPSG:28354';
        } else {
            zone = 'EPSG:28355';
        }
        return zone;
    }

    extractTenement(batchIdAndTenement) {
        if ((undefined === batchIdAndTenement)
            || (null == batchIdAndTenement)) {
                alert("No batch ID and Tenement");
            } else {
                var strings = batchIdAndTenement.split(":");
                if (2 === strings.length) {
                    return strings;
                } else {
                    alert("Unknown format of batch ID and Tenement string, should be 123:xxx, but got: " + batchIdAndTenement);
                }
            }
    }
}