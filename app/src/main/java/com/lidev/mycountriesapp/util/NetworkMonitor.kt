package com.lidev.mycountriesapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Monitors the network status and provides a flow of boolean values indicating
 * whether the device is online or not.
 *
 * This class uses [ConnectivityManager.NetworkCallback] to receive real-time updates
 * about network availability and capabilities.
 */
class NetworkMonitor(
    context: Context,
) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isOnline = MutableStateFlow(false)

    /**
     * A [kotlinx.coroutines.flow.StateFlow] that emits true when the device is connected to the internet,
     * and false otherwise.
     */
    val isOnline = _isOnline.asStateFlow()

    /**
     * Set of currently valid networks that have internet capability.
     */
    private val validNetworks = mutableSetOf<Network>()

    private val networkCallback =
        object : ConnectivityManager.NetworkCallback() {
            /**
             * Called when a network becomes available.
             * We add the network to our set of valid networks and update the online status.
             */
            override fun onAvailable(network: Network) {
                synchronized(validNetworks) {
                    validNetworks.add(network)
                    _isOnline.value = validNetworks.isNotEmpty()
                }
            }

            /**
             * Called when a network is lost.
             * We remove the network from our set and update the online status.
             */
            override fun onLost(network: Network) {
                synchronized(validNetworks) {
                    validNetworks.remove(network)
                    _isOnline.value = validNetworks.isNotEmpty()
                }
            }

            /**
             * Called when the capabilities of a network change.
             * This is crucial for handling transitions between data and WiFi, or when a network
             * loses internet access while remaining connected.
             */
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities,
            ) {
                synchronized(validNetworks) {
                    val hasInternet =
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    if (hasInternet) {
                        validNetworks.add(network)
                    } else {
                        validNetworks.remove(network)
                    }
                    _isOnline.value = validNetworks.isNotEmpty()
                }
            }
        }

    init {
        // Configure a request to listen for networks with internet capability
        val networkRequest =
            NetworkRequest
                .Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        // Initial check to set the starting state based on the current active network
        val activeNetwork = connectivityManager.activeNetwork
        if (activeNetwork != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            if (capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true) {
                synchronized(validNetworks) {
                    validNetworks.add(activeNetwork)
                    _isOnline.value = true
                }
            }
        }
    }

    /**
     * Unregisters the network callback to prevent memory leaks.
     * Should be called when the monitor is no longer needed (e.g., in a ViewModel's onCleared).
     */
    fun unregister() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
