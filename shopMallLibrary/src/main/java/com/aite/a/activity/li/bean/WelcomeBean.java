package com.aite.a.activity.li.bean;

import java.io.Serializable;
import java.util.List;

/**
 * equipment_type机型必须	get
 */
public class WelcomeBean implements Serializable {

    /**
     * error_code : 0
     * message : 获取成功
     * datas : {"id":"2","equipment_type":"iphone5s","upload_images":[{"img_path":"https://aitecc.com/data/upload/aitemanger/app/guide_pages/06201500874422589.jpg","spec":"640x1136","size":244701},{"img_path":"https://aitecc.com/data/upload/aitemanger/app/guide_pages/06201500875678640.jpg","spec":"640x1136","size":360341},{"img_path":"https://aitecc.com/data/upload/aitemanger/app/guide_pages/06201500876140616.jpg","spec":"640x1136","size":344533}],"status":"1"}
     */

    private int error_code;
    private String message;
    private DatasBean datas;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 2
         * equipment_type : iphone5s
         * upload_images : [{"img_path":"https://aitecc.com/data/upload/aitemanger/app/guide_pages/06201500874422589.jpg","spec":"640x1136","size":244701},{"img_path":"https://aitecc.com/data/upload/aitemanger/app/guide_pages/06201500875678640.jpg","spec":"640x1136","size":360341},{"img_path":"https://aitecc.com/data/upload/aitemanger/app/guide_pages/06201500876140616.jpg","spec":"640x1136","size":344533}]
         * status : 1
         */

        private String id;
        private String equipment_type;
        private String status;
        private List<UploadImagesBean> upload_images;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEquipment_type() {
            return equipment_type;
        }

        public void setEquipment_type(String equipment_type) {
            this.equipment_type = equipment_type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<UploadImagesBean> getUpload_images() {
            return upload_images;
        }

        public void setUpload_images(List<UploadImagesBean> upload_images) {
            this.upload_images = upload_images;
        }

        public static class UploadImagesBean {
            /**
             * img_path : https://aitecc.com/data/upload/aitemanger/app/guide_pages/06201500874422589.jpg
             * spec : 640x1136
             * size : 244701
             */

            private String img_path;
            private String spec;
            private int size;

            public String getImg_path() {
                return img_path;
            }

            public void setImg_path(String img_path) {
                this.img_path = img_path;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }
    }
}
