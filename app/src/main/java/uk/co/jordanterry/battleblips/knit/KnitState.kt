package uk.co.jordanterry.battleblips.knit

interface KnitState

inline fun <reified T : KnitState> KnitState.transform(): T = this as T