export default async function ({store, redirect}) {
  if (!store.getters.hasToken) {
    redirect('/login?message=Unauthorized')
  }
}
