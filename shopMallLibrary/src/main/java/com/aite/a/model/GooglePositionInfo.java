package com.aite.a.model;

import java.util.List;

/**
 * @author:TQX
 * @Date: 2019/10/28
 * @description:
 */
public class GooglePositionInfo {

    /**
     * results : [{"address_components":[{"long_name":"9-2","short_name":"9-2","types":["street_number"]},{"long_name":"蓮潭路","short_name":"蓮潭路","types":["route"]},{"long_name":"左營區","short_name":"左營區","types":["administrative_area_level_3","political"]},{"long_name":"高雄市","short_name":"高雄市","types":["administrative_area_level_1","political"]},{"long_name":"台湾","short_name":"TW","types":["country","political"]},{"long_name":"813","short_name":"813","types":["postal_code"]}],"formatted_address":"813台湾高雄市左營區蓮潭路9-2號","geometry":{"location":{"lat":22.681032,"lng":120.2914228},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":22.6823809802915,"lng":120.2927717802915},"southwest":{"lat":22.6796830197085,"lng":120.2900738197085}}},"place_id":"ChIJFfPGIaIFbjQR7BO7SoJnxxo","plus_code":{"compound_code":"M7JR+CH 台湾高雄市鼓山區高雄","global_code":"7QJ2M7JR+CH"},"types":["street_address"]}]
     * status : OK
     */

    private String status;
    private List<ResultsBean> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * address_components : [{"long_name":"9-2","short_name":"9-2","types":["street_number"]},{"long_name":"蓮潭路","short_name":"蓮潭路","types":["route"]},{"long_name":"左營區","short_name":"左營區","types":["administrative_area_level_3","political"]},{"long_name":"高雄市","short_name":"高雄市","types":["administrative_area_level_1","political"]},{"long_name":"台湾","short_name":"TW","types":["country","political"]},{"long_name":"813","short_name":"813","types":["postal_code"]}]
         * formatted_address : 813台湾高雄市左營區蓮潭路9-2號
         * geometry : {"location":{"lat":22.681032,"lng":120.2914228},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":22.6823809802915,"lng":120.2927717802915},"southwest":{"lat":22.6796830197085,"lng":120.2900738197085}}}
         * place_id : ChIJFfPGIaIFbjQR7BO7SoJnxxo
         * plus_code : {"compound_code":"M7JR+CH 台湾高雄市鼓山區高雄","global_code":"7QJ2M7JR+CH"}
         * types : ["street_address"]
         */

        private String formatted_address;
        private GeometryBean geometry;
        private String place_id;
        private PlusCodeBean plus_code;
        private List<AddressComponentsBean> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public PlusCodeBean getPlus_code() {
            return plus_code;
        }

        public void setPlus_code(PlusCodeBean plus_code) {
            this.plus_code = plus_code;
        }

        public List<AddressComponentsBean> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponentsBean> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class GeometryBean {
            /**
             * location : {"lat":22.681032,"lng":120.2914228}
             * location_type : ROOFTOP
             * viewport : {"northeast":{"lat":22.6823809802915,"lng":120.2927717802915},"southwest":{"lat":22.6796830197085,"lng":120.2900738197085}}
             */

            private LocationBean location;
            private String location_type;
            private ViewportBean viewport;

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public ViewportBean getViewport() {
                return viewport;
            }

            public void setViewport(ViewportBean viewport) {
                this.viewport = viewport;
            }

            public static class LocationBean {
                /**
                 * lat : 22.681032
                 * lng : 120.2914228
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class ViewportBean {
                /**
                 * northeast : {"lat":22.6823809802915,"lng":120.2927717802915}
                 * southwest : {"lat":22.6796830197085,"lng":120.2900738197085}
                 */

                private NortheastBean northeast;
                private SouthwestBean southwest;

                public NortheastBean getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBean northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBean getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBean southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBean {
                    /**
                     * lat : 22.6823809802915
                     * lng : 120.2927717802915
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBean {
                    /**
                     * lat : 22.6796830197085
                     * lng : 120.2900738197085
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class PlusCodeBean {
            /**
             * compound_code : M7JR+CH 台湾高雄市鼓山區高雄
             * global_code : 7QJ2M7JR+CH
             */

            private String compound_code;
            private String global_code;

            public String getCompound_code() {
                return compound_code;
            }

            public void setCompound_code(String compound_code) {
                this.compound_code = compound_code;
            }

            public String getGlobal_code() {
                return global_code;
            }

            public void setGlobal_code(String global_code) {
                this.global_code = global_code;
            }
        }

        public static class AddressComponentsBean {
            /**
             * long_name : 9-2
             * short_name : 9-2
             * types : ["street_number"]
             */

            private String long_name;
            private String short_name;
            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }
    }
}
