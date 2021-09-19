package shop.programs

import cats.MonadThrow
import cats.data.NonEmptyList
import cats.syntax.all._
import shop.domain.{Card, OrderId, Orders, Payment, PaymentClient, ShoppingCart, UserId}

import scala.util.control.NoStackTrace

final case class Checkout[F[_] : MonadThrow](
  payments: PaymentClient[F],
  cart: ShoppingCart[F],
  orders: Orders[F]
) {
  private def ensureNonEmpty[A](xs: List[A]): F[NonEmptyList[A]] =
    MonadThrow[F].fromOption(
      NonEmptyList.fromList(xs),
      EmptyCartError
    )

  def process(userId: UserId, card: Card): F[OrderId] =
    for {
      c <- cart.get(userId)
      its <- ensureNonEmpty(c.items)
      pid <- payments.process(Payment(userId, c.total, card))
      oid <- orders.create(userId, pid, its, c.total)
      _ <- cart.delete(userId).attempt.void
    } yield oid
}

//TODO: move this?
case object EmptyCartError extends NoStackTrace

