# Advertisement aggregator

A module that helps to add advertisement in your Android app. This dependency provide an easy way to
add Admob, Applovin and Facebook ads in your app.

## Preconditions

1. Add SDK or API keys to manifest (SDK key for Applovin, API key for Admob)
2. Create an ad configuration in Firebase Remote Config. For each placement create a JSON array with
   following objects:

### Example of JSON for Remote Config

```json

[
  {
    "ads_provider": "admob",
    "is_test": "true",
    "id": "Your Admob placement ID",
    "priority": "1"
  },
  {
    "ads_provider": "applovin",
    "is_test": "true",
    "id": "Your Applovin placement ID",
    "priority": "2"
  },
  {
    "ads_provider": "none",
    "is_test": "true",
    "id": "",
    "priority": "3"
  }
]

```

In this example, we defined Ad configuration for Admob and Applovin interstitials. Order of the
objects is not important. Priority for Advertising Networks defined by `priority` value : the lower
the number, the higher priority. If advertisement for first Ad configuration cannot be loaded then
the next Ad config will be loaded. Use value `is_test` for development stage, if it is `true` - you
will see test ads.

## Usage

#### Retrieving ad configuration

To retrieve data from remote config we provide `AdRemoteConfigManager`. To get ad configuration from
Remote Config you can use `remoteConfigManager.loadInfoFromRemoteConfig().collect{...}`, which
preloads JSON from Remote Config and returns `Flow<RemoteConfigFetchStatus>` - if this methods
emits `FETCH_COMPLETED` data was fetched successfully, so you can use 
`remoteConfigManager.getAdRequest(adBlock: String)` to get ad configuration for separate ad placement.

#### Loading ad
To load advertisement use `AdLoadManager` class.
Call `loadAdFlow(CoroutineScope): Flow<InterstitialLoadState>` method.
`InterstitialLoadState` has 3 states: `Success`, `Loading` and `Completed`

```kotlin
sealed class InterstitialLoadState(
    open val adObject: BaseAdObject
) {
    data class Success(override val adObject: BaseAdObject) : InterstitialLoadState(adObject)
    data class Loading(override val adObject: BaseAdObject = BaseAdObject.NoneAdObject) : InterstitialLoadState(adObject)
    data class Completed(override val adObject: BaseAdObject = BaseAdObject.NoneAdObject) : InterstitialLoadState(adObject)
}
```

If ad was loaded successfully method `loadAdFlow(CoroutineScope)` will emit `Success(adObject)`
with loaded `adObject`.

#### Showing ad

To show ad use `AdShowManager` class. Call `showAd(adObject: BaseAdObject)` method. It shows
ad and returns `Flow<InterstitialShowState>`. `InterstitialShowState` has 3 states: `SHOWED`
, `FAILED_TO_SHOW`, `DISMISSED`.  





