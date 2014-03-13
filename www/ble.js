   
   var BLEHelper = function() {
    };

    /*
     * 
     * @param {type} successCallback(boolean)
     * @returns {undefined}
     */
    BLEHelper.prototype.isEnable = function(successCallback) {
        cordova.exec(
                successCallback, // success callback function
                null, // error callback function
                'BLEHelper', // mapped to our native Java class
                'isEnable', // with this action name
                [
                ]
                );
    };

    /*
     * 
     * @returns {undefined}
     */
    BLEHelper.prototype.enable = function() {
        cordova.exec(
                null, // success callback function
                null, // error callback function
                'BLEHelper', // mapped to our native Java class
                'enable', // with this action name
                [
                ]
                );
    };

    /*
     * 
     * @param {type} successCallback()
     * @param {type} errorCallback()
     * @returns {undefined}
     */
    BLEHelper.prototype.ibeaconDiscover = function(successCallback, errorCallback) {
        cordova.exec(
                successCallback, // success callback function
                errorCallback, // error callback function
                'BLEHelper', // mapped to our native Java class
                'ibeaconDiscover', // with this action name
                [
                ]
                );
    };

    /*
     * 
     * @param {type} successCallback()
     * @returns {undefined}
     */
    BLEHelper.prototype.ibeaconEndDiscover = function(successCallback) {
        cordova.exec(
                successCallback, // success callback function
                null, // error callback function
                'BLEHelper', // mapped to our native Java class
                'ibeaconEndDiscover', // with this action name
                [
                ]
                );
    };

    /*
     * 
     * @param {type} successCallback(boolean)
     * @returns {undefined}
     */
    BLEHelper.prototype.isDiscovering = function(successCallback) {
        cordova.exec(
                successCallback, // success callback function
                null, // error callback function
                'BLEHelper', // mapped to our native Java class
                'isDiscovering', // with this action name
                [
                ]
                );
    };

    /*
     * 
     * @param {type} successCallback(IBeaconPacket)
     * @param {type} errorCallback()
     * @returns {undefined}
     */
    BLEHelper.prototype.getIBeaconPacket = function(successCallback, errorCallback) {
        cordova.exec(
                successCallback, // success callback function
                errorCallback, // error callback function
                'BLEHelper', // mapped to our native Java class
                'getIBeaconPacket', // with this action name
                [
                ]
                );
    };

    var ble = new BLEHelper();
    module.exports = ble;

